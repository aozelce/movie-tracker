package com.aozelce.service;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

// Defines the base URI for all resource URIs.
@ApplicationPath("/service") // All REST endpoints will be under /service/*
public class MovieApplication extends Application {
    // The method returns a non-empty collection with classes, that must be included in the published JAX-RS application
    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> h = new HashSet<>();
        h.add(MovieResource.class);
        return h;
    }
}

