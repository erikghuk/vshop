package com.elg.vshop.entity.user;

import com.elg.vshop.validator.date.ValidDOB;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "utilisateur_details", schema = "vshop_schema")
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    @NotBlank
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String lastName;

    @Column(name = "dob")
    @NotNull(message = "La date de naissance ne doit pas être vide")
    @ValidDOB
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dob;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "utilisateur_id")
    private User user;

    public UserDetails() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
            this.user = user;
    }
}
