package com.aozelce.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type User.
 *
 * @author aozelce
 */
@Entity
@Table (name = "user")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int id;

    @Column (name = "cognito_id")
    private String cognitoId;

    @Column (name = "email")
    private String email;

    @Column (name = "username")
    private String username;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Recommendation> recommendations = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Source> sources = new ArrayList<>();


    /**
     * Instantiates a new User.
     */
    public User() {
    }

    /**
     * Instantiates a new User.
     *
     * @param email    the email
     * @param username the username
     */
    public User( String email, String username) {

        this.email = email;
        this.username = username;
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
     * Gets cognito id.
     *
     * @return the cognito id
     */
    public String getCognitoId() {
        return cognitoId;
    }

    /**
     * Sets cognito id.
     *
     * @param cognitoId the cognito id
     */
    public void setCognitoId(String cognitoId) {
        this.cognitoId = cognitoId;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets recommendations.
     *
     * @return the recommendations
     */
    public List<Recommendation> getRecommendations() {
        return recommendations;
    }

    /**
     * Sets recommendations.
     *
     * @param recommendations the recommendations
     */
    public void setRecommendations(List<Recommendation> recommendations) {
        this.recommendations = recommendations;
    }

    /**
     * Gets sources.
     *
     * @return the sources
     */
    public List<Source> getSources() {
        return sources;
    }

    /**
     * Sets sources.
     *
     * @param sources the sources
     */
    public void setSources(List<Source> sources) {
        this.sources = sources;
    }


    /**
     * Adds recommendation.
     *
     * @param recommendation the recommendation
     */
    public void addRecommendation(Recommendation recommendation) {
        recommendations.add(recommendation);
        recommendation.setUser(this);
    }

    /**
     * Adds source.
     * @param source the source
     */
    public void addSource(Source source) {
        sources.add(source);
        source.setUser(this);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", cognitoId='" + cognitoId + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId() == user.getId() && Objects.equals(getCognitoId(), user.getCognitoId()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getUsername(), user.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCognitoId(), getEmail(), getUsername());
    }
}
