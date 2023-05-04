package com.vijay.scoringengine.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping(value = "/bgimage", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<Resource> serveImage() throws IOException {
        Resource imageResource = resourceLoader.getResource("classpath:/static/images/linux.png");
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageResource);
    }
}