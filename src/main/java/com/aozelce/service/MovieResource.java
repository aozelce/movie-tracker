package com.aozelce.service;

import com.aozelce.entity.Media;
import com.aozelce.persistence.GenericDao;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/movies")
public class MovieResource {
    private final GenericDao<Media> mediaDao = new GenericDao<>(Media.class);

    // Get all movies from the database
    @GET
    @Produces("application/json")
    public Response getAllMovies() {
        List<Media> media = mediaDao.getAll();
        return Response.status(200).entity(media).build();
    }

    // Get a movie by its ID from the database
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getMovieById(@PathParam("id") int id) {
        Media media = mediaDao.getById(id);
        return Response.status(200).entity(media).build();
    }

    // Get a movie by its ID from the database and return only the title as HTML
    @GET
    @Path("/{id}/title")
    @Produces("text/html")
    public Response getMovieTitle(@PathParam("id") int id) {
        Media media = mediaDao.getById(id);
        if (media == null) {
            return Response.status(404).entity("<h1>Movie not found</h1>").build();
        }
        String title = media.getTitle();
        String html = "<html><body><h1>" + title + "</h1></body></html>";
        return Response.status(200).entity(html).build();
    }
}
