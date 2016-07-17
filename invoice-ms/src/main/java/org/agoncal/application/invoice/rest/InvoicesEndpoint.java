package org.agoncal.application.invoice.rest;

import io.swagger.annotations.Api;
import org.agoncal.application.invoice.model.Invoice;
import org.agoncal.application.invoice.service.InvoiceService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.constraints.Max;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
}
