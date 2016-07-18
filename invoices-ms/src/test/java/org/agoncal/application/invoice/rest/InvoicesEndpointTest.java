package org.agoncal.application.invoice.rest;

import org.agoncal.application.invoice.model.Invoice;
import org.agoncal.application.invoice.model.InvoiceLine;
import org.agoncal.application.invoice.service.InvoiceService;
import org.agoncal.application.invoice.util.Discount;
import org.agoncal.application.invoice.util.RateProducer;
import org.agoncal.application.invoice.util.ResourceProducer;
import org.agoncal.application.invoice.util.Vat;
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
import javax.ws.rs.client.Entity;
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
public class InvoicesEndpointTest {

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
            .addClass(Invoice.class)
            .addClass(InvoiceLine.class)
            .addClass(RestApplication.class)
            .addClass(InvoicesEndpoint.class)
            .addClass(InvoiceService.class)
            .addClass(Discount.class)
            .addClass(Vat.class)
            .addClass(RateProducer.class)
            .addClass(ResourceProducer.class)
            .addAsResource("META-INF/persistence-test.xml", "META-INF/persistence.xml")
            .addAsResource("import_h2_invoices-test.sql")
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
    public void should_find_invoice() {
        String body = webTarget.path("1001").request(MediaType.APPLICATION_JSON).get(String.class);
        assertThatJson(body).isEqualTo("{\"id\":1001,\"version\":1,\"invoiceDate\":\"2016-01-01\",\"totalBeforeDiscount\":100.0,\"discountRate\":11.0,\"discount\":11.0,\"totalAfterDiscount\":90.0,\"vatRate\":6.0,\"vat\":5.0,\"totalAfterVat\":120.0,\"firstName\":\"Obi-Wan\",\"lastName\":\"Kenobi\",\"telephone\":\"+1 765 8977 321\",\"email\":\"obiwan@kenobi.com\",\"street1\":\"75B High Wighcombe\",\"street2\":null,\"city\":\"New York\",\"state\":null,\"zipcode\":\"4532\",\"country\":\"UNITED STATES\",\"items\":[],\"month\":0}");
    }

    @Test
    public void should_not_find_invoice() {
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), webTarget.path("666").request(MediaType.APPLICATION_JSON).get().getStatus());
    }

    @Test
    public void should_reach_max_invoice_id() {
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), webTarget.path("999999").request(MediaType.APPLICATION_JSON).get().getStatus());
    }

    @Test
    public void should_create_an_invoice() {
        Invoice invoice = new Invoice("John", "Smith", "john@smith.com", "Ritherdon Rd", "Brighton", "SW817", "UK");
        invoice.setTelephone("+44 34567 789");
        invoice.setStreet2("Corner right");
        invoice.addInvoiceLine(new InvoiceLine("Help", 12.99F, 1));
        invoice.addInvoiceLine(new InvoiceLine("Java EE 7", 18.99F, 2));

        assertEquals(Response.Status.CREATED.getStatusCode(), webTarget.request().post(Entity.entity(invoice, MediaType.APPLICATION_XML)).getStatus());
    }
}
