package com.elg.vshop.entity.vehicule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "boite_vitesse", schema = "vshop_schema")
@JsonIgnoreProperties("vehicleList")
public class Gearbox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "type_boite")
    private String boxName;

    @OneToMany(mappedBy = "gearbox", fetch = FetchType.LAZY)
    private List<Vehicle> vehicleList;

    public Gearbox() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBoxName() {
        return boxName;
    }

    public void setBoxName(String boxName) {
        this.boxName = boxName;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Gearbox gearbox = (Gearbox) o;

        return boxName != null ? boxName.equals(gearbox.boxName) : gearbox.boxName == null;
    }

    @Override
    public int hashCode() {
        return boxName != null ? boxName.hashCode() : 0;
    }
}
