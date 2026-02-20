package com.aozelce.entity;

import jakarta.persistence.*;

/**
 * The type Recommendation.
 *
 * @author aozelce
 */
@Entity
@Table(name = "recommendation")
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "source_id", nullable = false)
    private Source source;

    @ManyToOne
    @JoinColumn(name = "media_id", nullable = false)
    private Media media;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "is_watched")
    private boolean isWatched;

    /**
     * Instantiates a new Recommendation.
     */
    public Recommendation() {
    }

    /**
     * Instantiates a new Recommendation.
     *
     * @param user      the user
     * @param source    the source
     * @param media     the media
     * @param notes     the notes
     * @param isWatched the is watched
     */
    public Recommendation(User user, Source source, Media media, String notes, boolean isWatched) {
        this.user = user;
        this.source = source;
        this.media = media;
        this.notes = notes;
        this.isWatched = isWatched;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets source.
     *
     * @return the source
     */
    public Source getSource() {
        return source;
    }

    /**
     * Sets source.
     *
     * @param source the source
     */
    public void setSource(Source source) {
        this.source = source;
    }

    /**
     * Gets media.
     *
     * @return the media
     */
    public Media getMedia() {
        return media;
    }

    /**
     * Sets media.
     *
     * @param media the media
     */
    public void setMedia(Media media) {
        this.media = media;
    }

    /**
     * Gets notes.
     *
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets notes.
     *
     * @param notes the notes
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Is watched boolean.
     *
     * @return the boolean
     */
    public boolean isWatched() {
        return isWatched;
    }

    /**
     * Sets watched.
     *
     * @param watched the watched
     */
    public void setWatched(boolean watched) {
        isWatched = watched;
    }



    @Override
    public String toString() {
        return "Recommendation{" +
                "id=" + id +
                ", user=" + user +
                ", source=" + source +
                ", media=" + media +
                ", notes='" + notes + '\'' +
                ", isWatched=" + isWatched +
                '}';
    }
}