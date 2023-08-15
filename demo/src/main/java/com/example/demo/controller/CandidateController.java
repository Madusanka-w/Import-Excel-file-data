package com.example.demo.controller;

import com.example.demo.service.CandidateService;
import org.apache.catalina.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class CandidateController {

    private CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;

    }

    @PostMapping("/upload")
    public String upload(
            @RequestParam MultipartFile file,
            @RequestParam Integer numberOfSheet)
            throws Exception {
        return candidateService.upload(file, numberOfSheet);
    }
}
