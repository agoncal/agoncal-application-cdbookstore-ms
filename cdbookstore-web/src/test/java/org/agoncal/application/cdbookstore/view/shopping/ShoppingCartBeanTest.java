package org.agoncal.application.cdbookstore.view.shopping;

import org.agoncal.application.cdbookstore.model.*;
import org.agoncal.application.cdbookstore.registry.DefaultRegistry;
import org.agoncal.application.cdbookstore.registry.ServiceRegistry;
import org.agoncal.application.cdbookstore.util.ResourceProducer;
import org.agoncal.application.cdbookstore.view.account.AccountBean;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.assertNotNull;

@RunWith(Arquillian.class)
public class ShoppingCartBeanTest {

    // ======================================
    // =          Injection Points          =
    // ======================================

    @Inject
    private ShoppingCartBean shoppingCartBean;

    // ======================================
    // =         Deployment methods         =
    // ======================================

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap
            .create(JavaArchive.class)
            .addClass(ShoppingCartBean.class)
            .addClass(AccountBean.class)
            .addClass(User.class)
            .addClass(UserRole.class)
            .addClass(Address.class)
            .addClass(CreditCard.class)
            .addClass(CreditCardType.class)
            .addClass(ShoppingCart.class)
            .addClass(ShoppingCartItem.class)
            .addClass(ResourceProducer.class)
            .addClass(DefaultRegistry.class)
            .addClass(ServiceRegistry.class)
            .addAsManifestResource("META-INF/persistence-test.xml", "persistence.xml")
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    // ======================================
    // =            Test methods            =
    // ======================================

    @Test
    public void should_be_deployed() {
        assertNotNull(shoppingCartBean);
    }
}
