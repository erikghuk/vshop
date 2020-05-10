package com.elg.vshop.entity.user;

import com.elg.vshop.validator.password.PasswordFormat;
import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "compte", schema = "vshop_schema")
//@JsonIgnoreProperties("user")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "email", nullable = false)
    @NotNull(message = "{name.not.empty}")
    @NotEmpty(message = "Email ne doit pas être vide")
    @Email(message = "{email.not.valid}")
    private String email;

    @Column(name = "pass", nullable = false)
    @Size(min = 6, message = "La taille  doit être min 6 simbol")
    @PasswordFormat
    @NotNull(message = "{name.not.empty}")
    @NotEmpty(message = "{name.not.empty}")
    private String password;

    @Transient
    private String passwordConfirm;

    @Column(name = "active")
    private boolean active = true;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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
