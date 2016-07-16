package org.agoncal.application.topbooks.rest;

import io.swagger.annotations.Api;
import org.agoncal.application.topbooks.model.Book;

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
@Api(description = "Top Books Endpoint")
public class TopBooksEndpoint {

    @Inject
    private EntityManager em;

    @Inject
    private Logger logger;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getTopBooks() {
        List<Book> books = new ArrayList<>();

        int min = em.createQuery("select min (b.id) from Book b", Long.class).getSingleResult().intValue();
        int max = em.createQuery("select max (b.id) from Book b", Long.class).getSingleResult().intValue();

        while (books.size() < 5) {
            long id = new Random().nextInt((max - min) + 1) + min;
            Book book = em.find(Book.class, id);
            if (book != null)
                books.add(book);
        }

        logger.info("Top Books are " + books);
        return books;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Book getTopBook(@PathParam("id") @Max(9999) Long id) {

        Book book = em.find(Book.class, id);

        logger.info("Top Book is " + book);
        return book;
    }
}
