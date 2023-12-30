package com.oasisuniformes.inventario.service;

import com.oasisuniformes.inventario.entity.Role;
import com.oasisuniformes.inventario.entity.Userdata;
import com.oasisuniformes.inventario.model.request.LoginRequest;
import com.oasisuniformes.inventario.model.request.RegisterRequest;
import com.oasisuniformes.inventario.model.response.AuthResponse;
import com.oasisuniformes.inventario.repository.RoleRepository;
import com.oasisuniformes.inventario.repository.UserdataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserdataRepository userdataRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;


    public AuthResponse loginDB(LoginRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user= userdataRepository.findByUsername(request.getUsername()).orElseThrow();
        String token=jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();

    }


    public Role createRole(Role role){
        return roleRepository.save(role);
    }



    public AuthResponse register(RegisterRequest request) {

        Role role = roleRepository.findById(request.getIdRole())
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + request.getIdRole()));

        Userdata user = Userdata.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode( request.getPassword()))
                .role(role)
                .build();

        userdataRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();

    }

}
