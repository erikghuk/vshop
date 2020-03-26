package com.elg.vshop.entity.vehicule;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "marque", schema="vshop_schema")
public class Marque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "marque_name")
    private String marqueName;

    @OneToMany(mappedBy = "marque", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Model> models;

    @OneToMany(mappedBy = "marques", fetch = FetchType.LAZY)
    private List<Car> cars;

    public Marque() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarqueName() {
        return marqueName;
    }

    public void setMarqueName(String marqueName) {
        this.marqueName = marqueName;
    }

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
