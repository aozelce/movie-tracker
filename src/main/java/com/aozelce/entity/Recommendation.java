package com.aozelce.entity;

/**
 * The type Recommendation.
 *
 * @author aozelce
 */
public class Recommendation {

    private int id;
    private String userId;
    private String sourceId;
    private String mediaId;
    private String notes;
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
    public Recommendation(int id, String userId, String sourceId, String mediaId, String notes, boolean isWatched) {
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
    public String getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets source id.
     *
     * @return the source id
     */
    public String getSourceId() {
        return sourceId;
    }

    /**
     * Sets source id.
     *
     * @param sourceId the source id
     */
    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    /**
     * Gets media id.
     *
     * @return the media id
     */
    public String getMediaId() {
        return mediaId;
    }

    /**
     * Sets media id.
     *
     * @param mediaId the media id
     */
    public void setMediaId(String mediaId) {
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
                ", userId='" + userId + '\'' +
                ", sourceId='" + sourceId + '\'' +
                ", mediaId='" + mediaId + '\'' +
                ", notes='" + notes + '\'' +
                ", isWatched=" + isWatched +
                '}';
    }
}
