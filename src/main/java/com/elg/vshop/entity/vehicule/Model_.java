package com.elg.vshop.entity.vehicule;

import com.elg.vshop.entity.vehicule.Marque;
import com.elg.vshop.entity.vehicule.Model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Model.class)
public class Model_ {
    public static volatile SingularAttribute<Model, Marque> marque;
}
