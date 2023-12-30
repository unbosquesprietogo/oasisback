package com.oasisuniformes.inventario.repository;

import com.oasisuniformes.inventario.entity.Userdata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserdataRepository extends JpaRepository<Userdata, Integer> {

    Optional<Userdata> findByUsername(String username);

}
