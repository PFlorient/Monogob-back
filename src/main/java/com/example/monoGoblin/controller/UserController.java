package com.example.monoGoblin.controller;

import com.example.monoGoblin.dto.ApiResponse;
import com.example.monoGoblin.dto.UserDto;
import com.example.monoGoblin.dto.auth.LoginResponse;
import com.example.monoGoblin.manager.UserManager;
import com.example.monoGoblin.security.JWTService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;
import java.util.UUID;

@RequestMapping("/api/v1/user")
@RestController
public class UserController {

    private final UserManager userManager;
    private final JWTService jwtService;

    @Autowired
    public UserController(UserManager userManager, JWTService jwtService) {
        this.userManager = userManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> create(@Valid @RequestBody UserDto userdto) {
        this.userManager.register(userdto);
        return ResponseEntity.ok(ApiResponse.success(null, "success"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        LoginResponse response = this.userManager.login(username, password);
        return ResponseEntity.ok(ApiResponse.success(response, "success"));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<?>> delete() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization").substring(7);

        UUID uuidUser = jwtService.extractUserUuid(token);
        this.userManager.delete(uuidUser);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(null, "success"));
    }
}
