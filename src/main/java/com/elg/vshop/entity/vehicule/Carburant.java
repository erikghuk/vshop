package com.elg.vshop.entity.vehicule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "carburant", schema = "vshop_schema")
@JsonIgnoreProperties("vehicleList")
public class Carburant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "type_carb")
    private String typeOfCarburant;

    @OneToMany(mappedBy = "carburant", fetch = FetchType.LAZY)
    private List<Vehicle> vehicleList;

    public Carburant() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeOfCarburant() {
        return typeOfCarburant;
    }

    public void setTypeOfCarburant(String typeOfCarburant) {
        this.typeOfCarburant = typeOfCarburant;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }
}
