package uz.pdp.appatmsystam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appatmsystam.entity.User;
import uz.pdp.appatmsystam.payload.ApiResponse;
import uz.pdp.appatmsystam.payload.LoginDto;
import uz.pdp.appatmsystam.payload.RegisterDto;
import uz.pdp.appatmsystam.service.AuthService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public HttpEntity<?> register(@RequestBody RegisterDto registerDto) {
        ApiResponse response = authService.registerUser(registerDto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @GetMapping("/verifyEmail")
    public HttpEntity<?> verifyEmail(@RequestParam String emailCode, @RequestParam String email) {
        ApiResponse response = authService.verifyEmail(emailCode, email);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto loginDto) {
        ApiResponse response = authService.login(loginDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 401).body(response);
    }

}
