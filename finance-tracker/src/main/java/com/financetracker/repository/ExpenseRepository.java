package com.financetracker.repository;

import com.financetracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
/*
* This interface extends JpaRepository, which provides CRUD operations for the Expense entity.
* The JpaRepository interface takes two parameters: the type of the entity (Expense) and the type of the primary key (UUID).
* The @Repository annotation indicates that this interface is a Spring Data repository, which will be automatically implemented by Spring Data JPA at runtime.
*/
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {




}
