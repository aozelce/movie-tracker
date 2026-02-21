package com.aozelce.entity;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * The type Source.
 *
 * @author aozelce
 */
@Entity
@Table(name = "source")
public class Source {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "name")
    private String name;

    /**
     * Instantiates a new Source.
     */
    public Source() {
    }

    /**
     * Instantiates a new Source.
     *
     * @param id   the id
     * @param user the user
     * @param name the name
     */
    public Source(int id, User user, String name) {
        this.id = id;
        this.user = user;
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
                "id=" + id +
                ", user=" + user.getId() +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Source source = (Source) o;
        return getId() == source.getId() && Objects.equals(getUser(), source.getUser()) && Objects.equals(getName(), source.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getName());
    }
}