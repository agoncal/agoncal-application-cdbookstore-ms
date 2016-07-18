package org.agoncal.application.cdbookstore.registry;

import org.agoncal.application.cdbookstore.util.Auditable;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */

@ApplicationScoped
@Auditable
public class DefaultRegistry implements ServiceRegistry {

    public URI getTopRatedCDsServiceURI() {

        try {
            URI MASTER_URI = new URI("http://localhost:8080/msTopCDs");
            URI SLAVE_URI = new URI("http://localhost:8081/msTopCDs");

            Response response;

            // Tries on MASTER
            try {
                response = ClientBuilder.newClient().target(MASTER_URI).request().get();
                if (response.getStatus() == Response.Status.OK.getStatusCode())
                    return MASTER_URI;
            } catch (Exception e) {
                // swallow exception
            }

            // MASTER didn't work, try on SLAVE
            try {
                response = ClientBuilder.newClient().target(SLAVE_URI).request().get();
                if (response.getStatus() == Response.Status.OK.getStatusCode())
                    return SLAVE_URI;
            } catch (Exception e) {
                return null;
            }

        } catch (URISyntaxException e) {
            return null;
        }
        return null;
    }

    public URI getTopRatedBooksServiceURI() {

        try {
            URI MASTER_URI = new URI("http://localhost:8080/msTopBooks");
            URI SLAVE_URI = new URI("http://localhost:8082/msTopBooks");

            Response response;

            // Tries on MASTER
            try {
                response = ClientBuilder.newClient().target(MASTER_URI).request().get();
                if (response.getStatus() == Response.Status.OK.getStatusCode())
                    return MASTER_URI;
            } catch (Exception e) {
                // swallow exception
            }

            // MASTER didn't work, try on SLAVE
            try {
                response = ClientBuilder.newClient().target(SLAVE_URI).request().get();
                if (response.getStatus() == Response.Status.OK.getStatusCode())
                    return SLAVE_URI;
            } catch (Exception e) {
                return null;
            }

        } catch (URISyntaxException e) {
            return null;
        }
        return null;
    }

    public URI getInvoiceServiceURI() {

        try {
            URI MASTER_URI = new URI("http://localhost:8080/msInvoices");
            URI SLAVE_URI = new URI("http://localhost:8083/msInvoices");

            Response response;

            // Tries on MASTER
            try {
                response = ClientBuilder.newClient().target(MASTER_URI).request().get();
                if (response.getStatus() == Response.Status.OK.getStatusCode())
                    return MASTER_URI;
            } catch (Exception e) {
                // swallow exception
            }

            // MASTER didn't work, try on SLAVE
            try {
                response = ClientBuilder.newClient().target(SLAVE_URI).request().get();
                if (response.getStatus() == Response.Status.OK.getStatusCode())
                    return SLAVE_URI;
            } catch (Exception e) {
                return null;
            }

        } catch (URISyntaxException e) {
            return null;
        }
        return null;
    }
}
