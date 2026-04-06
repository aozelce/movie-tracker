package com.aozelce.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/movies")
public class MovieResource {
    // The Java method will process HTTP GET requests
    @GET
    @Path("/{id}") // Accepts a path parameter
    @Produces("text/plain")
    public Response getMessage(@PathParam("id") String id) {
        // Return a personalized message
        String output = "Details for movie Id: " + id;
        return Response.status(200).entity(output).build();
    }

}

