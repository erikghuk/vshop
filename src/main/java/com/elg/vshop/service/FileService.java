package com.elg.vshop.service;

import com.elg.vshop.entity.Annonce;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    void uploadFile(MultipartFile[] file, Annonce annonce);
}
