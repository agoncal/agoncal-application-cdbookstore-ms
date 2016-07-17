package org.agoncal.application.test;

import org.agoncal.application.cdbookstore.view.shopping.ShoppingCart;
import org.agoncal.application.cdbookstore.view.shopping.ShoppingCartItem;
import org.agoncal.application.invoice.model.Invoice;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
public class MarshallingTest {

    @Test
    public void should_marshall_shopping_cart_to_invoice() throws JAXBException {
        ShoppingCart shoppingCart = new ShoppingCart("John", "Smith", "john@smith.com", "Ritherdon Rd", "Brighton", "SW817", "UK");
        shoppingCart.setTelephone("+44 34567 789");
        shoppingCart.setStreet2("Corner right");
        shoppingCart.addItem(new ShoppingCartItem("Help", 12.99F, 1));
        shoppingCart.addItem(new ShoppingCartItem("Java EE 7", 18.99F, 2));

        // Shopping Cart to XML
        StringWriter writer = new StringWriter();
        JAXBContext context = JAXBContext.newInstance(ShoppingCart.class);

        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(shoppingCart, writer);

        Unmarshaller unmarshaller = context.createUnmarshaller();
        shoppingCart = (ShoppingCart) unmarshaller.unmarshal(new StringReader(writer.toString()));
        Assert.assertEquals("UK", shoppingCart.getCountry());
        Assert.assertEquals("Corner right", shoppingCart.getStreet2());
        Assert.assertEquals(2, shoppingCart.getItems().size());

        // XML to Invoice
        context = JAXBContext.newInstance(Invoice.class);

        unmarshaller = context.createUnmarshaller();
        Invoice invoice = (Invoice) unmarshaller.unmarshal(new StringReader(writer.toString()));
        Assert.assertEquals("UK", invoice.getCountry());
        Assert.assertEquals("Corner right", invoice.getStreet2());
        Assert.assertEquals(2, invoice.getItems().size());
    }
}
