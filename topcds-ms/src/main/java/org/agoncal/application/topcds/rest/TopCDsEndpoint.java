package org.agoncal.application.topcds.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;
import java.util.logging.Logger;

@Path("/")
public class TopCDsEndpoint {

    @Inject
    private Logger logger;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getTopCDs() {
        StringJoiner sj = new StringJoiner(", ");

        List<Integer> randomCDs = getRandomNumbers();
        for (Integer randomCD : randomCDs) {
            sj.add("{\"id\":" + randomCD.toString() + "}");
        }
        return "[" + sj.toString() + "]";
    }

    private List<Integer> getRandomNumbers() {
        List<Integer> randomCDs = new ArrayList<>();
        Random r = new Random();
        randomCDs.add(r.nextInt(100) + 1101);
        randomCDs.add(r.nextInt(100) + 1101);
        randomCDs.add(r.nextInt(100) + 1101);
        randomCDs.add(r.nextInt(100) + 1101);
        randomCDs.add(r.nextInt(100) + 1101);

        logger.info("Top CDs are " + randomCDs);

        return randomCDs;
    }
}
