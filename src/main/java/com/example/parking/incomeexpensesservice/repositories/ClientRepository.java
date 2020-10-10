package com.example.parking.incomeexpensesservice.repositories;

import com.example.parking.incomeexpensesservice.model.Client;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
}
