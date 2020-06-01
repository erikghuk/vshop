package com.elg.vshop.controller.main;

import com.elg.vshop.dao.CarburantRepository;
import com.elg.vshop.dao.GearboxRepository;
import com.elg.vshop.dao.MarqueRepository;
import com.elg.vshop.dao.ModelRepository;
import com.elg.vshop.entity.vehicule.Carburant;
import com.elg.vshop.entity.vehicule.Gearbox;
import com.elg.vshop.entity.vehicule.Marque;
import com.elg.vshop.entity.vehicule.Model;
import com.elg.vshop.exception.InvalidDataException;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
    private CarburantRepository carbRepository;

    @Autowired
    public VehicleRestController(MarqueRepository marqueRepository, ModelRepository modelRepository, GearboxRepository boxRepository, CarburantRepository carbRepository) {
        this.marqueRepository = marqueRepository;
        this.modelRepository = modelRepository;
        this.boxRepository = boxRepository;
        this.carbRepository = carbRepository;
    }

    @GetMapping("/marques")
    List<Marque> getAllMarques() {
        List<Marque> marques = marqueRepository.findAllByOrderByMarqueName();
        if(marques == null) {
            throw new InvalidDataException("Pas de marques dans la BDD");
        }
        return marques ;
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
        List<Model> models = modelRepository.findAllByMarqueId(marqueId);
        if(models == null) {
            throw new InvalidDataException("L'ID de marque n'est pas valide");
        }
        return models;
    }

    @GetMapping("/gearboxes")
    List<Gearbox> getAllBoxes() {
        List<Gearbox> gearboxes = boxRepository.findAll();
        if(gearboxes == null) {
            throw new InvalidDataException("Pas de boxes dans la BDD");
        }
        return gearboxes;
    }

    @GetMapping("/carbs")
    List<Carburant> getAllCarbs() {
        List<Carburant> carbs = carbRepository.findAll();
        if(carbs == null) {
            throw new InvalidDataException("Pas de carburant dans la BDD");
        }
        return carbs;
    }
}
