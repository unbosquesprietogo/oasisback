package com.oasisuniformes.inventario.controller;

import com.oasisuniformes.inventario.entity.Role;
import com.oasisuniformes.inventario.model.request.LoginRequest;
import com.oasisuniformes.inventario.model.request.RegisterRequest;
import com.oasisuniformes.inventario.model.response.AuthResponse;
import com.oasisuniformes.inventario.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping(value ="/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.loginDB(request));
    }


    @PostMapping(value ="/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping(value ="/role")
    public ResponseEntity<Role> role(@RequestBody Role role){
        return ResponseEntity.ok(authService.createRole(role));
    }


}
