package com.elg.vshop.entity.vehicule;

import com.elg.vshop.entity.vehicule.Marque;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Marque.class)
public class Marque_ {
    public static volatile SingularAttribute<Marque, String> marqueName;
}
