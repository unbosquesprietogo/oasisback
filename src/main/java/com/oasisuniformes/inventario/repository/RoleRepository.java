package com.oasisuniformes.inventario.repository;

import com.oasisuniformes.inventario.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {


}
