package com.aozelce.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", cognitoId='" + cognitoId + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
