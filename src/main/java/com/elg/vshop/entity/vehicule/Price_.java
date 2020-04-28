package com.elg.vshop.entity.vehicule;

import com.elg.vshop.entity.Annonce;
import com.elg.vshop.entity.vehicule.Price;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Price.class)
public class Price_ {
    public static volatile SingularAttribute<Price, Integer> amount;
}
