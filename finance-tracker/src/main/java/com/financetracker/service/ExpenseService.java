package com.financetracker.service;

import com.financetracker.model.Expense;
import com.financetracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ExpenseService {

    private final ExpenseRepository repository;

    public ExpenseService(ExpenseRepository repository) {
        this.repository = repository;
    }

    public List<Expense> getAllExpenses() {
        // This is a placeholder implementation. In a real application, you would retrieve expenses from a database.
        return repository.findAll();

    }

    // This method is a placeholder for saving an expense.
    public Expense saveExpense(Expense expense) {
        return repository.save(expense);

    }

    public void deleteExpense(UUID id) {
        repository.deleteById(id);

    }


}
