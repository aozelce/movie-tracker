package com.aozelce.entity;

/**
 * The type Source.
 *
 * @author aozelce
 */
public class Source {

    private int id;
    private String userId;
    private String name;

    /**
     * Instantiates a new Source.
     */
    public Source() {
    }

    /**
     * Instantiates a new Source.
     *
     * @param id     the id
     * @param userId the user id
     * @param name   the name
     */
    public Source(int id, String userId, String name) {
        this.id = id;
        this.userId = userId;
        this.name = name;
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
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Source{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
