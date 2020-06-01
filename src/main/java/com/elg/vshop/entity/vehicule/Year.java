package com.elg.vshop.entity.vehicule;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "annee", schema = "vshop_schema")
@JsonIgnoreProperties("vehicleList")
public class Year {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date_prod")
    @Min(value = 1920, message = "ce champ ne peut pas être moins de 1920")
    @Max(value = 2020, message = "ce champ ne peut pas être superieur de 2020")
    private Integer productionDate;

    @OneToMany(mappedBy = "year", fetch = FetchType.LAZY)
    private List<Vehicle> vehicleList;

    public Year() {
    }

    private Integer getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Integer productionDate) {
        this.productionDate = productionDate;
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

        Year year = (Year) o;

        return productionDate.equals(year.productionDate);
    }

    @Override
    public int hashCode() {
        return productionDate.hashCode();
    }
}
