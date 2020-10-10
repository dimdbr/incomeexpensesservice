package com.example.parking.incomeexpensesservice.repositories;

import com.example.parking.incomeexpensesservice.model.CommunalWorker;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommunalWorkerRepository extends JpaRepository<CommunalWorker, UUID> {
}
