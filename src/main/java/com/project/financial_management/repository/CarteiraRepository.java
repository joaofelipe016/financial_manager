package com.project.financial_management.repository;

import com.project.financial_management.entity.Carteira;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CarteiraRepository extends JpaRepository<Carteira, UUID> {
}
