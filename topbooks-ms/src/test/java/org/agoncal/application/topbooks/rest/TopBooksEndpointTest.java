package org.agoncal.application.topbooks.rest;

import org.agoncal.application.topbooks.model.Book;
import org.agoncal.application.topbooks.utils.ResourceProducer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
@RunAsClient
public class TopBooksEndpointTest {

    // ======================================
    // =          Injection Points          =
    // ======================================

    @ArquillianResource
    private URI baseURL;

    // ======================================
    // =         Deployment methods         =
    // ======================================

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap
            .create(WebArchive.class)
            .addClass(Book.class)
            .addClass(RestApplication.class)
            .addClass(TopBooksEndpoint.class)
            .addClass(ResourceProducer.class)
            .addAsResource("META-INF/persistence-test.xml", "META-INF/persistence.xml")
            .addAsResource("import_h2.sql", "import_h2.sql")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    // ======================================
    // =            Test methods            =
    // ======================================

    @Test
    public void should_be_deployed() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(baseURL);
        assertEquals(Response.Status.OK.getStatusCode(), target.request(MediaType.APPLICATION_JSON).get().getStatus());
    }
}
