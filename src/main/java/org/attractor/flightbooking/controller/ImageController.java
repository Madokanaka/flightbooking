package org.attractor.flightbooking.controller;


import lombok.RequiredArgsConstructor;
import org.attractor.flightbooking.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping
    public String uploadImage(@AuthenticationPrincipal org.springframework.security.core.userdetails.User principal, MultipartFile file) {
        imageService.uploadImage(principal, file);
        return "redirect:/company";
    }

}
