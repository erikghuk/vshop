package com.elg.vshop.service;

import com.elg.vshop.entity.Annonce;
import com.elg.vshop.entity.ImageAnnonce;
import com.elg.vshop.exception.FileStorageException;
import com.elg.vshop.exception.InvalidDataException;
import com.elg.vshop.service.security.CurrentAuthenticatedUser;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FIleServiceImpl implements FileService {
    @Value("${app.upload.dir}")
    private String uploadPath;

    @Value("${image.convert.size}")
    private Integer imageSizeThreshold;

    private CurrentAuthenticatedUser authenticatedUser;

    @Autowired
    public FIleServiceImpl(CurrentAuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }

    @Override
    public void uploadFile(MultipartFile[] files, Annonce annonce) {
        int id = authenticatedUser.getUserId();
            String rootPath = System.getProperty("user.dir");
            File uploadDir = new File(rootPath + uploadPath + "/" + id);
            if(!uploadDir.exists()) {
                uploadDir.mkdir();
            }

        if(files != null && files.length > 0) {
            for (MultipartFile multipartFile : files) {
                String uuidName = UUID.randomUUID().toString();
                String fileName = uuidName + "." + multipartFile.getOriginalFilename();
                try {
                    File outputFile = new File(uploadDir + "/" + fileName);
                    createThumbnail(multipartFile, imageSizeThreshold, outputFile);
                } catch (FileStorageException | IOException e) {
                    e.getMessage();
                }
                ImageAnnonce imageAnnonce = new ImageAnnonce(id + "/" + fileName);
                annonce.addImage(imageAnnonce);
            }
        } else {
            String fileName = "default-cars.png";
            ImageAnnonce imageAnnonce = new ImageAnnonce(fileName);
            annonce.addImage(imageAnnonce);
        }
    }

    private void createThumbnail(MultipartFile orginalFile, Integer width, File outputFile) throws IOException{
        BufferedImage thumbImg = null;
        BufferedImage img = ImageIO.read(orginalFile.getInputStream());
        if(img == null) {
            throw new InvalidDataException("Invalid data format, .jpg, .png expected");
        }
        thumbImg = Scalr.resize(img, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, width, Scalr.OP_ANTIALIAS);
        ImageIO.write(thumbImg, orginalFile.getContentType().split("/")[1] , outputFile);
    }
}
