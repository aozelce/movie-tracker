package com.aozelce.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * The type Media.
 *
 * @author aozelce
 */
@Entity
@Table(name = "media")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private int id;

    @Column(name = "tmdb_id")
    private int tmdbId;

    @Column(name = "title")
    private String title;

    @Column(name = "media_type")
    private String mediaType;

    @Column(name = "year")
    private int year;

    @Column(name = "poster_path")
    private String posterPath;

    @Column(name = "overview", columnDefinition = "TEXT")
    private String overview;

    @Column(name = "genres")
    private String genres;
}