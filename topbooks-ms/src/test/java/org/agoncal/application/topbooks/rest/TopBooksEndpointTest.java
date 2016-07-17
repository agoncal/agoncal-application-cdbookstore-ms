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
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.net.URI;

import static net.javacrumbs.jsonunit.fluent.JsonFluentAssert.assertThatJson;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
@RunAsClient
public class TopBooksEndpointTest {

    // ======================================
    // =          Injection Points          =
    // ======================================

    @ArquillianResource
    private URI baseURL;
    private Client client;
    private WebTarget webTarget;


    // ======================================
    // =         Deployment methods         =
    // ======================================

    @Deployment(testable = false)
    public static WebArchive createDeployment() {

        // Import Maven runtime dependencies
        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
            .importRuntimeDependencies().resolve().withTransitivity().asFile();

        return ShrinkWrap
            .create(WebArchive.class)
            .addClass(Book.class)
            .addClass(RestApplication.class)
            .addClass(TopBooksEndpoint.class)
            .addClass(ResourceProducer.class)
            .addAsResource("META-INF/persistence-test.xml", "META-INF/persistence.xml")
            .addAsResource("import_h2_topbooks-test.sql")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            .addAsLibraries(files);
    }

    // ======================================
    // =          Lifecycle methods         =
    // ======================================

    @Before
    public void initWebTarget() {
        client = ClientBuilder.newClient();
        webTarget = client.target(baseURL);
    }

    // ======================================
    // =            Test methods            =
    // ======================================

    @Test
    public void should_be_deployed() {
        assertEquals(Response.Status.OK.getStatusCode(), webTarget.request(MediaType.APPLICATION_JSON).get().getStatus());
    }

    @Test
    public void should_have_five_items() {
        String body = webTarget.request(MediaType.APPLICATION_JSON).get(String.class);
        assertThatJson(body).isArray().ofLength(5);
        assertTrue(body.startsWith("[{\"id\":"));
    }

    @Test
    public void should_find_book() {
        String body = webTarget.path("1001").request(MediaType.APPLICATION_JSON).get(String.class);
        assertThatJson(body).isEqualTo("{\"id\":1001,\"isbn\":\"1931182310\"}");
    }

    @Test
    public void should_not_find_book() {
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), webTarget.path("666").request(MediaType.APPLICATION_JSON).get().getStatus());
    }

    @Test
    public void should_reach_max_book_id() {
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), webTarget.path("999999").request(MediaType.APPLICATION_JSON).get().getStatus());
    }

}
