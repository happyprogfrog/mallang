package me.progfrog.mallang.socialmultiplication.controller;

import lombok.RequiredArgsConstructor;
import me.progfrog.mallang.socialmultiplication.service.AdminService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("test")
@RequiredArgsConstructor
@RestController
@RequestMapping("/multiplication/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/delete-db")
    public ResponseEntity deleteDatabase() {
        adminService.deleteDatabaseContents();
        return ResponseEntity.ok().build();
    }
}
