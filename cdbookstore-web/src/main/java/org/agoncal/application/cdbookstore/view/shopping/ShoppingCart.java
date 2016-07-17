package org.agoncal.application.cdbookstore.view.shopping;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
@XmlRootElement(name = "invoice")
public class ShoppingCart {

    // ======================================
    // =             Attributes             =
    // ======================================

    @NotNull
    @Size(min = 2, max = 50)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 50)
    private String lastName;
    private String telephone;

    @NotNull
    private String email;

    @Size(min = 5, max = 50)
    @NotNull
    private String street1;
    private String street2;

    @Size(min = 2, max = 50)
    @NotNull
    private String city;
    private String state;

    @Size(min = 1, max = 10)
    @NotNull
    private String zipcode;
    private String country;

    private List<ShoppingCartItem> items;

    // ======================================
    // =            Constructors            =
    // ======================================

    public ShoppingCart() {
    }

    public ShoppingCart(String firstName, String lastName, String email, String street1, String city, String zipcode, String country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.street1 = street1;
        this.city = city;
        this.country = country;
        this.zipcode = zipcode;
    }

    public ShoppingCart(String firstName, String lastName, String email, String street1, String city, String zipcode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.street1 = street1;
        this.city = city;
        this.zipcode = zipcode;
    }

    public ShoppingCart(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // ======================================
    // =        Getters and Setters         =
    // ======================================

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<ShoppingCartItem> getItems() {
        return items;
    }

    public void setItems(List<ShoppingCartItem> items) {
        this.items = items;
    }

    public void addItem(ShoppingCartItem item) {
        if (items == null)
            items = new ArrayList<>();
        items.add(item);
    }

    // ======================================
    // =   Methods hash, equals, toString   =
    // ======================================

    @Override
    public String toString() {
        return "ShoppingCart{" +
            "firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", telephone='" + telephone + '\'' +
            ", email='" + email + '\'' +
            ", street1='" + street1 + '\'' +
            ", street2='" + street2 + '\'' +
            ", city='" + city + '\'' +
            ", state='" + state + '\'' +
            ", zipcode='" + zipcode + '\'' +
            ", country='" + country + '\'' +
            ", items=" + items +
            '}';
    }
}
