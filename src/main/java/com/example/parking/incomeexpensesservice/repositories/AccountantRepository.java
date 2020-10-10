package com.example.parking.incomeexpensesservice.repositories;

import com.example.parking.incomeexpensesservice.model.Accountant;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountantRepository extends JpaRepository<Accountant,Integer> {
}
