package com.elg.vshop.entity.user;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ville", schema="vshop_schema")
public class Ville {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "ville_name")
    private String villeName;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "depart_id")
    private Departement departement;


    @OneToMany(mappedBy = "ville", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<UserDetails> users;

    public Ville() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<UserDetails> getUsers() {
        return users;
    }

    public void setUsers(List<UserDetails> users) {
        this.users = users;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public String getVilleName() {
        return villeName;
    }

    public void setVilleName(String villeName) {
        this.villeName = villeName;
    }
}
