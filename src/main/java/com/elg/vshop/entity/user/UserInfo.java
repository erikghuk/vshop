package com.elg.vshop.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "utilisateur_details", schema = "vshop_schema")
@JsonIgnoreProperties("user")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    @Pattern(regexp = "^[\\p{L} .'-]+$", message = "Ce n'est pas un prénom")
    private String firstName;

    @Column(name = "last_name")
    @Pattern(regexp = "^[\\p{L} .'-]+$", message = "Ce n'est pas un nom")
    private String lastName;

    @Column(name = "dob")
    @Past(message = "{dob.must.be.past}")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dob;

    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "utilisateur_id")
    private User user;

    public UserInfo() {
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
