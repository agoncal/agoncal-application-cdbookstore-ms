package org.agoncal.application.invoice.rest;

import io.swagger.annotations.Api;
import org.agoncal.application.invoice.model.Invoice;
import org.agoncal.application.invoice.service.InvoiceService;

import javax.inject.Inject;
import javax.validation.constraints.Max;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

@Path("/")
@Api(description = "Invoices Endpoint")
public class InvoicesEndpoint {

    @Inject
    private InvoiceService invoiceService;

    @Inject
    private Logger logger;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Invoice> getInvoices() {

        List<Invoice> invoices = invoiceService.listAll();

        logger.info("All invoices " + invoices);
        return invoices;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Invoice getInvoice(@PathParam("id") @Max(9999) Long id) {

        Invoice invoice = invoiceService.findById(id);

        logger.info("Invoice is " + invoice);
        return invoice;
    }

    @POST
    public Response create(Invoice invoice) {
        invoice = invoiceService.persist(invoice);
        URI uri = UriBuilder.fromResource(InvoicesEndpoint.class).path(String.valueOf(invoice.getId())).build();

        logger.info("URI is " + uri);
        return Response.created(uri).build();
    }
}
