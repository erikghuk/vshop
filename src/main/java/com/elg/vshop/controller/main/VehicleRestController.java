package com.elg.vshop.controller.main;

import com.elg.vshop.dao.GearboxRepository;
import com.elg.vshop.dao.MarqueRepository;
import com.elg.vshop.dao.ModelRepository;
import com.elg.vshop.entity.vehicule.Gearbox;
import com.elg.vshop.entity.vehicule.Marque;
import com.elg.vshop.entity.vehicule.Model;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vh")
@CrossOrigin("http://localhost:4200")
public class VehicleRestController {
    private MarqueRepository marqueRepository;
    private ModelRepository modelRepository;
    private GearboxRepository boxRepository;

    @Autowired
    public VehicleRestController(MarqueRepository marqueRepository, ModelRepository modelRepository, GearboxRepository boxRepository) {
        this.marqueRepository = marqueRepository;
        this.modelRepository = modelRepository;
        this.boxRepository = boxRepository;
    }

    @GetMapping("/marques")
    List<Marque> getAllMarques() {
        return marqueRepository.findAllByOrderByMarqueName();
    }

    @GetMapping("/marques/{marqueId}")
    public Marque getMarqueById(@PathVariable int marqueId) throws NotFoundException {
        Optional<Marque> result = marqueRepository.findById(marqueId);
        Marque marque = null;
        if(result.isPresent()) {
            marque = result.get();
        } else {
            throw new NotFoundException("there is no marque with id " + marqueId);
        }
        return marque;
    }

    @GetMapping("/models/{marqueId}")
    public List<Model> getModelsByMarque(@PathVariable int marqueId) {
        return modelRepository.findAllByMarqueId(marqueId);
    }

    @GetMapping("/gearboxes")
    List<Gearbox> getAllBoxes() {
        return boxRepository.findAll();
    }
}
