package com.elg.vshop.dao;

import com.elg.vshop.entity.Annonce_;
import com.elg.vshop.entity.Annonce;
import com.elg.vshop.entity.vehicule.*;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class AnnonceSpecification implements Specification<Annonce> {
    private AnnonceSearch criteria;

    public AnnonceSpecification(AnnonceSearch annonceSearch) {
        this.criteria = annonceSearch;
    }

    @Override
    public Predicate toPredicate(Root<Annonce> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Path<Vehicle> vehiclePath = root.get(Annonce_.vehicle);
        Path<Model> modelPath = vehiclePath.get(Vehicle_.model);
        Path<Marque> marquePath = modelPath.get(Model_.marque);
        Path<Price> pricePath = vehiclePath.get(Vehicle_.price);
        Path<Gearbox> gearboxPath = vehiclePath.get(Vehicle_.gearbox);
        Path<Integer> kilometrage = vehiclePath.get(Vehicle_.km);
        Path<Year> yearPath = vehiclePath.get(Vehicle_.year);


        List<Predicate> predicates = new ArrayList<>();

        // Marque et Model
        if(criteria.getModel() != null && checkForString(criteria.getModel().getModelName())) {
            predicates.add(
                    criteriaBuilder.equal(modelPath.get("modelName"), criteria.getModel().getModelName())
            );
        } else if(criteria.getMarque() != null && checkForString(criteria.getMarque().getMarqueName())) {
            predicates.add(
                    criteriaBuilder.equal(marquePath.get("marqueName"), criteria.getMarque().getMarqueName())
            );
        }

        // Price
        if(criteria.getPriceStart() !=null && criteria.getPriceEnd() != null) {
            predicates.add(
                    criteriaBuilder.between(pricePath.get("amount"), criteria.getPriceStart().getAmount(), criteria.getPriceEnd().getAmount())
            );
        }

        // Gearbox
        if(criteria.getGearbox() != null) {
            predicates.add(
                    criteriaBuilder.equal(gearboxPath.get("boxName"), criteria.getGearbox().getBoxName())
            );
        }

        // kilometrage
        if(criteria.getKmStart() != null && criteria.getKmEnd() != null) {
            predicates.add(
                    criteriaBuilder.between(vehiclePath.get("km"), criteria.getKmStart(), criteria.getKmEnd())
            );
        }

        if(criteria.getDateStart() != null && criteria.getDateEnd() != null) {
            predicates.add(criteriaBuilder.between(yearPath.get("productionDate"), criteria.getDateStart().getProductionDate(), criteria.getDateEnd().getProductionDate())
            );
        }
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("creationDate")));
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }

    private boolean checkForString(String text) {
        return text != null && !text.equals("");
    }
}
