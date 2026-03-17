package com.aozelce.persistence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TmdbDaoTest {

    @Test
    void getMovieSuccess() {
        TmdbDao dao = new TmdbDao();
        assertEquals("The Matrix", dao.getMovie().getResults().get(0).getTitle());
    }
}