package com.example.imdb_clone.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('Admin')")
public class AdminController {

    @GetMapping("/dashboard")
    public ResponseEntity<String> getDashboard(){
        return ResponseEntity.ok("Admin Dashboard'a hoş geldin.");
    }

}
