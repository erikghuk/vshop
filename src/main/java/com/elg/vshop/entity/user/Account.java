package com.elg.vshop.entity.user;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "compte", schema = "vshop_schema")
@JsonIgnoreProperties("user")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "pass", nullable = false)
    private String password;

    @Transient
    private String passwordConfirm;

    @Column(name = "active")
    private boolean active;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "utilisateur_id")
    private User user;

    @Column(name = "permission")
    private String permissions;


    @Column(name = "user_role")
    private String role;

    @ManyToMany
    private Set<Role> roles;

    public Account() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String roles) {
        this.role = role;
    }

    public Set<Role> getRoleSet() {
        if(roles.size() > 0) {
            return roles;
        } else
            return new HashSet<>();
    }

    public List<String> getRoleList() {
        if(role.length() > 0) {
            return Arrays.asList(role.split(","));
        } else
            return new ArrayList<>();
    }

    public List<String> getPermissionList() {
        if(permissions.length() > 0) {
            return Arrays.asList(permissions.split(","));
        } else
            return new ArrayList<>();
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
