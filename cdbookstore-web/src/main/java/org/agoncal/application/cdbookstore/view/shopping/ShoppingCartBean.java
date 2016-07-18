package org.agoncal.application.cdbookstore.view.shopping;

import org.agoncal.application.cdbookstore.model.*;
import org.agoncal.application.cdbookstore.registry.ServiceRegistry;
import org.agoncal.application.cdbookstore.view.account.AccountBean;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.validation.Validator;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Antonio Goncalves http://www.antoniogoncalves.org --
 */

@Named
@SessionScoped
public class ShoppingCartBean implements Serializable {

    // ======================================
    // =          Injection Points          =
    // ======================================

    @Inject
    private FacesContext facesContext;
    @Inject
    private AccountBean accountBean;
    @Inject
    private EntityManager em;
    @Inject
    private Logger logger;
    @Inject
    private Validator validator;
    @Inject
    private ServiceRegistry serviceRegistry;

    // ======================================
    // =             Attributes             =
    // ======================================

    private List<ShoppingCartItem> cartItems = new ArrayList<>();
    private Address address = new Address();
    private String country = "";
    private CreditCard creditCard = new CreditCard();

    // ======================================
    // =          Business methods          =
    // ======================================

    public String addItemToCart() {
        Item item = em.find(Item.class, getParamId("itemId"));

        boolean itemFound = false;
        for (ShoppingCartItem cartItem : cartItems) {
            // If item already exists in the shopping cart we just change the quantity
            if (cartItem.equals(item)) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                itemFound = true;
            }
        }
        if (!itemFound)
            // Otherwise it's added to the shopping cart
            cartItems.add(new ShoppingCartItem(item.getId(), item.getSmallImageURL(), item.getTitle(), item.getUnitCost(), 1));

        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, item.getTitle() + " added to the shopping cart",
            "You can now add more stuff if you want"));

        return "/shopping/viewItem.xhtml?faces-redirect=true&includeViewParams=true";
    }

    public String removeItemFromCart() {
        Item item = em.find(Item.class, getParamId("itemId"));

        for (ShoppingCartItem cartItem : cartItems) {
            if (cartItem.equals(item)) {
                cartItems.remove(cartItem);
                return null;
            }
        }

        return null;
    }

    public String updateQuantity() {
        return null;
    }

    public String confirmation() {

        // Creating the invoice
        User user = accountBean.getUser();
        ShoppingCart shoppingCart = new ShoppingCart(user.getFirstName(), user.getLastName(), user.getEmail(), address.getStreet1(), address.getCity(), address.getZipcode(), country);
        shoppingCart.setTelephone(user.getTelephone());
        shoppingCart.setStreet2(address.getStreet2());
        shoppingCart.setItems(cartItems);

        // Validating the invoice
        validator.validate(shoppingCart);

        // Sending the invoice
        URI serviceURI = serviceRegistry.getTopRatedBooksServiceURI();
        if (serviceURI != null) {
            Response response = ClientBuilder.newClient().target(serviceURI).request().post(Entity.entity(shoppingCart, MediaType.APPLICATION_JSON));

            if (response != null && response.getStatus() != Response.Status.CREATED.getStatusCode()) {
                logger.severe("Invoice could not be sent to " + serviceURI + " due to response code " + response.getStatus());
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Order could not be created",
                    "Problem connecting to the Invoice service " + response.getStatus()));
            }

            logger.severe("An invoice has been sent to " + serviceURI);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Order created",
                "You will receive a confirmation email"));
        } else {
            logger.severe("Invoice could not be sent");
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Order could not be created",
                "Problem connecting to the Invoice service"));
        }

        return "/main";
    }

    public List<ShoppingCartItem> getCartItems() {
        return cartItems;
    }

    public boolean shoppingCartIsEmpty() {
        return getCartItems() == null || getCartItems().size() == 0;
    }

    public Float getTotal() {
        if (cartItems == null || cartItems.isEmpty())
            return 0f;

        Float total = 0f;

        // Sum up the quantities
        for (ShoppingCartItem cartItem : cartItems) {
            total += (cartItem.getSubTotal());
        }
        return total;
    }

    protected Long getParamId(String param) {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        return Long.valueOf(map.get(param));
    }

    // ======================================
    // =        Getters and Setters         =
    // ======================================

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public CreditCardType[] getCreditCardTypes() {
        return CreditCardType.values();
    }

    public String[] getCountries() {
        TypedQuery<Country> query = em.createNamedQuery(Country.FIND_ALL, Country.class);
        List<Country> countries = query.getResultList();
        String[] result = new String[countries.size()];
        for (int i = 0; i < countries.size(); i++) {
            result[i] = countries.get(i).getName();
        }
        return result;
    }
}
