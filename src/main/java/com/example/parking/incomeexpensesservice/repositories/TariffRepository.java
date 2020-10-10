package com.example.parking.incomeexpensesservice.repositories;

import com.example.parking.incomeexpensesservice.model.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TariffRepository extends JpaRepository<Tariff,Integer> {
}
