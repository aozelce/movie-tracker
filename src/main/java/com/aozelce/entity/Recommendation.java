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

    @Column(name = "user_id")
    private int userId;

    @Column(name = "source_id")
    private int sourceId;

    @Column(name = "media_id")
    private int mediaId;

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
     * @param id        the id
     * @param userId    the user id
     * @param sourceId  the source id
     * @param mediaId   the media id
     * @param notes     the notes
     * @param isWatched the is watched
     */
    public Recommendation(int id, int userId, int sourceId, int mediaId, String notes, boolean isWatched) {
        this.id = id;
        this.userId = userId;
        this.sourceId = sourceId;
        this.mediaId = mediaId;
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
     * Gets user id.
     *
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets source id.
     *
     * @return the source id
     */
    public int getSourceId() {
        return sourceId;
    }

    /**
     * Sets source id.
     *
     * @param sourceId the source id
     */
    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    /**
     * Gets media id.
     *
     * @return the media id
     */
    public int getMediaId() {
        return mediaId;
    }

    /**
     * Sets media id.
     *
     * @param mediaId the media id
     */
    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
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
                ", userId=" + userId +
                ", sourceId=" + sourceId +
                ", mediaId=" + mediaId +
                ", notes='" + notes + '\'' +
                ", isWatched=" + isWatched +
                '}';
    }
}