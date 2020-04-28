package com.elg.vshop.entity.vehicule;

import com.elg.vshop.entity.vehicule.Gearbox;
import com.elg.vshop.entity.vehicule.Model;
import com.elg.vshop.entity.vehicule.Price;
import com.elg.vshop.entity.vehicule.Vehicle;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Vehicle.class)
public class Vehicle_ {

    public static volatile SingularAttribute<Vehicle, Model> model;
    public static volatile SingularAttribute<Vehicle, Gearbox> gearbox;
    public static volatile SingularAttribute<Vehicle, Integer> km;
    public static volatile SingularAttribute<Vehicle, Price> price;
    public static volatile SingularAttribute<Vehicle, Year> year;
}
