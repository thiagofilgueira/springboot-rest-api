package com.mysample.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author thiagofilgueira
 */
@Data
@Entity
@Table(name = "user")
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName="id") }, inverseJoinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName="id") })
    private List<Role> roles;

    public ApplicationUser() {};

    public ApplicationUser(String username, String password, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

}