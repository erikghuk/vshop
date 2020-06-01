package com.elg.vshop.entity;

import com.elg.vshop.entity.user.User;
import com.elg.vshop.entity.vehicule.Vehicle;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "annonce", schema = "vshop_schema")
public class Annonce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "titre")
    @Size(min = 10, message = "la taille doit être entre 10-100")
    @NotNull(message = "{name.not.empty}")
    private String title;

    @Column(name = "description")
    @Size(min = 10, max = 2000, message = "la taille doit être entre 10-2000")
    @NotNull(message = "{name.not.empty}")
    private String description;

    @Column(name = "active")
    private boolean active;

    @Column(name = "date_d_annonce")
    @CreationTimestamp
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private Date creationDate;

    @Column(name = "dernier_msj")
    @UpdateTimestamp
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private Date modifDate;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "utilisateur_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id")
    private Vehicle vehicle;

    @OneToMany(mappedBy = "annonce",
            cascade = CascadeType.ALL)
    private List<ImageAnnonce> images;

    public Annonce() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModifDate() {
        return modifDate;
    }

    public void setModifDate(Date modifDate) {
        this.modifDate = modifDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public List<ImageAnnonce> getImages() {
        return images;
    }

    public void setImages(List<ImageAnnonce> images) {
        this.images = images;
    }

    public void addImage(ImageAnnonce imageAnnonce) {
        if (images ==  null) {
            images = new ArrayList<>();
        }
        images.add(imageAnnonce);
        imageAnnonce.setAnnonce(this);
    }
}
