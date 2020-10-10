package com.example.parking.incomeexpensesservice.service;

import com.example.parking.incomeexpensesservice.model.Accountant;

import javassist.NotFoundException;

import java.util.List;

public interface ExpensesIncomeService {

    Accountant createAccountant(Accountant accountant);
    List<Accountant> readAllAccountants();
    Accountant readAccountant(int id) throws NotFoundException;
    Accountant updateSalary(int id,double salary) throws NotFoundException;
    void setPayPriceForClients() throws NotFoundException;

}
