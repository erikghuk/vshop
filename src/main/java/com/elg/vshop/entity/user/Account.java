package com.elg.vshop.entity.user;

import com.elg.vshop.validator.password.PasswordFormat;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "compte", schema = "vshop_schema")
@JsonIgnoreProperties("user")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "email", nullable = false)
    @Email
    @NotEmpty(message = "Cette valeur ne doit pas être vide")
    @NotNull(message = "Cette valeur ne doit pas être vide")
    private String email;

    @Column(name = "pass", nullable = false)
    @NotEmpty(message = "Cette valeur ne doit pas être vide")
    @Size(min = 6, message = "La taille minimum doit être 6 simbol")
    @PasswordFormat
    private String password;

    @Transient
    private String passwordConfirm;

    @Column(name = "active")
    private boolean active = true;

    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "utilisateur_id")
    private User user;

    @ManyToOne(fetch=FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "role_id")
    @JsonBackReference
    private Role role;

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
