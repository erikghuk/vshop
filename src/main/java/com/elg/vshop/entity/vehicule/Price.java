package com.elg.vshop.entity.vehicule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Table(name = "prix", schema = "vshop_schema")
@JsonIgnoreProperties("vehicleList")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "montant")
    @Min(value = 0, message = "Le montant ne peut pas être moins de zero")
    private int amount;

    @OneToMany(mappedBy = "price", fetch = FetchType.LAZY)
    private List<Vehicle> vehicleList;

    public Price() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Price price = (Price) o;

        return amount == price.amount;
    }

    @Override
    public int hashCode() {
        return amount;
    }
}
