package org.attractor.flightbooking.service.impl;



import org.attractor.flightbooking.dto.UserDto;
import org.attractor.flightbooking.exception.ResourceNotFoundException;

import org.attractor.flightbooking.service.ImageService;
import org.attractor.flightbooking.service.UserService;
import org.attractor.flightbooking.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.attractor.flightbooking.model.User;


@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {


    private final FileUtil fileUtil;
    private final UserService userService;


    @Override
    public ResponseEntity<?> findByName(String fileName) {
        log.info("Fetching image by fileName={}", fileName);
        return fileUtil.getOutputFile(fileName, "images/", MediaType.IMAGE_JPEG);
    }


    @Override
    public void uploadImage(org.springframework.security.core.userdetails.User principal, MultipartFile file) {
        User user = userService.findByEmail(principal.getUsername());
        log.info("Saving image for userId={}", user);

        String fileName = fileUtil.saveUploadFile(file, "images/");

        userService.saveAvatar(user.getEmail(), fileName);

        log.debug("Image saved with fileName={}", fileName);
    }
}
