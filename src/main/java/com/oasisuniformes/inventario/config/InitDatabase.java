package com.oasisuniformes.inventario.config;

import com.oasisuniformes.inventario.entity.Role;
import com.oasisuniformes.inventario.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitDatabase {

    @Bean
    CommandLineRunner initRoles(RoleRepository repository) {
        return args -> {
            Role adminRole = repository.findByRoleName("admin");
            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setRoleName("admin");
                repository.save(adminRole);
            }
        };
    }
}


