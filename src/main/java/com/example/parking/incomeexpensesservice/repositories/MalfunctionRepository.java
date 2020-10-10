package com.example.parking.incomeexpensesservice.repositories;

import com.example.parking.incomeexpensesservice.model.Malfunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MalfunctionRepository extends JpaRepository<Malfunction, UUID> {
    @Query(value = "SELECT * from income_expenses_service.malfunctions where ispayed=?1 ",nativeQuery = true)
    List<Malfunction> readMalfunctionByPayed(boolean ispayed);

}
