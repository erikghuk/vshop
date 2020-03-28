package com.elg.vshop.entity.user;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "utilisateur", schema = "vshop_schema")
@JsonIgnoreProperties("userDetails")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "date_inscription")
    @CreationTimestamp
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dateRegistration;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Account account;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private UserDetails userDetails;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {

        this.userDetails = userDetails;
    }

    public Date getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(Date dateRestration) {
        this.dateRegistration = dateRestration;
    }
}
