package com.elg.vshop.entity;

import com.elg.vshop.entity.Annonce;
import com.elg.vshop.entity.vehicule.Vehicle;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


@StaticMetamodel(Annonce.class)
public class Annonce_ {
    public static volatile SingularAttribute<Annonce, Vehicle> vehicle;
}
