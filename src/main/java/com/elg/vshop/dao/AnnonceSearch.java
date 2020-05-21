package com.elg.vshop.dao;

import com.elg.vshop.entity.vehicule.*;

import javax.validation.constraints.*;
import java.util.Date;

public class AnnonceSearch {
    private String title;

    private Marque marque;
    private Model model;


    private Price priceStart;
    private Price priceEnd;

    private Gearbox gearbox;
    private Carburant carburant;


    @Min(value = 0, message = "${number.min.value}")
    private Integer kmStart;

    @Max(value = 30000000, message = "${number.max.value}")
    private Integer kmEnd;


    private Year dateStart;

    private Year dateEnd;

    public AnnonceSearch() {
    }

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Price getPriceStart() {
        return priceStart;
    }

    public void setPriceStart(Price priceStart) {
        this.priceStart = priceStart;
    }

    public Price getPriceEnd() {
        return priceEnd;
    }

    public void setPriceEnd(Price priceEnd) {
        this.priceEnd = priceEnd;
    }

    public Gearbox getGearbox() {
        return gearbox;
    }

    public void setGearbox(Gearbox gearbox) {
        this.gearbox = gearbox;
    }


    public Year getDateStart() {
        return dateStart;
    }

    public void setDateStart(Year dateStart) {
        this.dateStart = dateStart;
    }

    public Year getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Year dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getKmStart() {
        return kmStart;
    }

    public void setKmStart(Integer kmStart) {
        this.kmStart = kmStart;
    }

    public Integer getKmEnd() {
        return kmEnd;
    }

    public void setKmEnd(Integer kmEnd) {
        this.kmEnd = kmEnd;
    }

    public Carburant getCarburant() {
        return carburant;
    }

    public void setCarburant(Carburant carburant) {
        this.carburant = carburant;
    }
}
