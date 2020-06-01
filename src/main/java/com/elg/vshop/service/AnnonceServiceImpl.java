package com.elg.vshop.service;

import com.elg.vshop.dao.*;
import com.elg.vshop.entity.Annonce;
import com.elg.vshop.entity.user.User;
import com.elg.vshop.entity.vehicule.Carburant;
import com.elg.vshop.entity.vehicule.Gearbox;
import com.elg.vshop.entity.vehicule.Model;
import com.elg.vshop.exception.AnnoncesNotFoundException;
import com.elg.vshop.exception.InvalidDataException;
import com.elg.vshop.exception.JwtAuthenticationException;
import com.elg.vshop.service.security.CurrentAuthenticatedUser;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AnnonceServiceImpl implements AnnonceService{

    private AnnonceRepository annonceRepository;
    private UserService userService;
    private ModelRepository modelRepository;
    private GearboxRepository gearboxRepository;
    private CarburantRepository carburantRepository;
    private CurrentAuthenticatedUser authenticatedUser;

    @Autowired
    public AnnonceServiceImpl(AnnonceRepository annonceRepository, UserService userService,
                              ModelRepository modelRepository, GearboxRepository gearboxRepository, CarburantRepository carburantRepository, CurrentAuthenticatedUser authenticatedUser) {
        this.annonceRepository = annonceRepository;
        this.userService = userService;
        this.modelRepository = modelRepository;
        this.gearboxRepository = gearboxRepository;
        this.carburantRepository = carburantRepository;
        this.authenticatedUser = authenticatedUser;
    }

    @Override
    public void saveAnnonce(Annonce annonce) {
        User user = authenticatedUser.getUser();
        if(annonce == null) {
            throw new InvalidDataException("Les données sont pas valides");
        }
        annonce.setUser(user);

        if(annonce.getVehicle() == null) {
            throw new InvalidDataException("Data n'est pas valide");
        }
        Model newModel = annonce.getVehicle().getModel();
        if(newModel == null) {
            throw new InvalidDataException("Data n'est pas valide");
        }
        Model existingModel = modelRepository.findByModelName(newModel.getModelName());
        if(existingModel == null) {
            throw new InvalidDataException("Data n'est pas valide");
        }


        Gearbox newGearBox = annonce.getVehicle().getGearbox();
        if(newGearBox != null) {
            Gearbox existingGearbox = gearboxRepository.findByBoxName(newGearBox.getBoxName());

            annonce.getVehicle().setGearbox(existingGearbox);
        }

        Carburant carburant = annonce.getVehicle().getCarburant();
        if(carburant != null) {
            Carburant existingCarburant = carburantRepository.findByTypeOfCarburant(carburant.getTypeOfCarburant());

            annonce.getVehicle().setCarburant(existingCarburant);
        }

        annonce.getVehicle().setModel(existingModel);
        annonceRepository.save(annonce);
    }

    @Override
    public void updateAnnonce(int annonceId, Annonce annonce) throws NotFoundException {
        Optional<Annonce> result = annonceRepository.findById(annonceId);
        Annonce existingAnnonce;
        if(result.isPresent()) {
            existingAnnonce = result.get();
        } else {
            throw new AnnoncesNotFoundException("Annonce not found");
        }
        if (annonce.getDescription() != null && !annonce.getDescription().equals(existingAnnonce.getDescription())) {
            existingAnnonce.setDescription(annonce.getDescription());
            annonceRepository.save(existingAnnonce);
        }
        if (annonce.getTitle() != null && !annonce.getTitle().equals(existingAnnonce.getTitle())) {
            existingAnnonce.setTitle(annonce.getTitle());
            annonceRepository.save(existingAnnonce);
        }
        if (annonce.getVehicle().getPrice() != null && !annonce.getVehicle().getPrice().equals(existingAnnonce.getVehicle().getPrice())) {
            existingAnnonce.getVehicle().setPrice(annonce.getVehicle().getPrice());
            annonceRepository.save(existingAnnonce);
        }

        if (annonce.getVehicle().getKm() != null && !annonce.getVehicle().getKm().equals(existingAnnonce.getVehicle().getKm())) {
            existingAnnonce.getVehicle().setKm(annonce.getVehicle().getKm());
            annonceRepository.save(existingAnnonce);
        }

        if (annonce.getVehicle().getYear() != null && !annonce.getVehicle().getYear().equals(existingAnnonce.getVehicle().getYear())) {
            existingAnnonce.getVehicle().setYear(annonce.getVehicle().getYear());
            annonceRepository.save(existingAnnonce);
        }
    }



    @Override
    public Set<Annonce> findAnnoncesByUserId() throws NotFoundException {
        Integer userId = authenticatedUser.getUserId();
        if(userId == 0) {
            throw new JwtAuthenticationException("Unauthorized access");
        }
        Set<Annonce> annoncesList = annonceRepository.findAllByUserId(userId);
        if(annoncesList == null || annoncesList.size() == 0) {
            throw new AnnoncesNotFoundException("Aucune annonce est trouvé");
        }
        return annoncesList;
    }

    @Override
    public List<Annonce> findByCriteria(AnnonceSearch annonceSearch) {
        Specification<Annonce> spec = new AnnonceSpecification(annonceSearch);
        List<Annonce> annonceList = annonceRepository.findAll(spec);

        annonceList.removeIf(annonce -> !annonce.getUser().getAccount().isActive());
        return annonceList;
    }


    @Override
    public Annonce deleteAnnonce(int annonceId) {
        Optional<Annonce> result = annonceRepository.findById(annonceId);
        Annonce annonce = null;
        if(result.isPresent()) {
            annonce = result.get();
        }
        if(annonce != null) {
            annonceRepository.deleteById(annonceId);
            return annonce;
        }
        return null;
    }

    @Override
    public long countById() {
        Integer userId = authenticatedUser.getUserId();
        if(userId == null) {
            throw new JwtAuthenticationException("Unauthorized access");
        }
        return annonceRepository.countByUserId(userId);
    }

    @Override
    public Annonce findById(int annonceId){
        if(annonceId < 0) {
            throw new InvalidDataException("Le format de data n'est pas valide");
        }
        Annonce annonce = null;
        Optional<Annonce> result = annonceRepository.findById(annonceId);

        if(result.isPresent()) {
            annonce = result.get();
        } else {
            try {
                throw  new AnnoncesNotFoundException("Aucune annonce a trouvé");
            } catch (AnnoncesNotFoundException e) {
                e.printStackTrace();
            }
        }
        return annonce;
    }

    @Override
    public List<Annonce> findLatestAnnonces() throws AnnoncesNotFoundException {
        List<Annonce> first10annonces = annonceRepository.findFirst10ByOrderByCreationDateDesc();
        if(first10annonces == null) {
            throw new AnnoncesNotFoundException("Annonce n'existe pas");
        }

        first10annonces.removeIf(annonce -> !annonce.getUser().getAccount().isActive());
        return first10annonces;
    }
}
