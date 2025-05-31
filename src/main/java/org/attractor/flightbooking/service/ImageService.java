package org.attractor.flightbooking.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {


    ResponseEntity<?> findByName(String fileName);


    void uploadImage(User principal, MultipartFile file);
}
