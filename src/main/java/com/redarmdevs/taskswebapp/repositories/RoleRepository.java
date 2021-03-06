package com.redarmdevs.taskswebapp.repositories;

import com.redarmdevs.taskswebapp.models.ERole;
import com.redarmdevs.taskswebapp.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}