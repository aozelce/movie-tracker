package com.aozelce.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * The type User.
 *
 * @author aozelce
 */
@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"recommendations", "sources"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @EqualsAndHashCode.Include
    private int id;

    @Column(name = "cognito_id")
    private String cognitoId;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Recommendation> recommendations = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Source> sources = new ArrayList<>();

    public User(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public void addRecommendation(Recommendation recommendation) {
        recommendations.add(recommendation);
        recommendation.setUser(this);
    }

    public void addSource(Source source) {
        sources.add(source);
        source.setUser(this);
    }
}