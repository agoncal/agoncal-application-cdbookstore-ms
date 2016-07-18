package org.agoncal.application.cdbookstore.registry;

import java.net.URI;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */

public interface ServiceRegistry {

    URI getTopRatedCDsServiceURI();

    URI getTopRatedBooksServiceURI();

    URI getInvoiceServiceURI();
}
