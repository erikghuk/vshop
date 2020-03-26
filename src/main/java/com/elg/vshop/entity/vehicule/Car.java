package com.elg.vshop.entity.vehicule;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "car", schema = "vshop_schema")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "kilometrage")
    private int km;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "marque_id")
    private Marque marques;

    public Car() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public Marque getMarques() {
        return marques;
    }

    public void setMarques(Marque marques) {
        this.marques = marques;
    }
}
