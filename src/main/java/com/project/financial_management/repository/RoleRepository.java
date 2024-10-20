package com.project.financial_management.repository;

import com.project.financial_management.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findByNmRole(String nmRole);
}
