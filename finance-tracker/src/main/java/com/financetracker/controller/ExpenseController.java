package com.financetracker.controller;

import com.financetracker.model.Expense;
import com.financetracker.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "http://localhost:5173") // Allow requests from the React frontend
public class ExpenseController {

    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }
    @GetMapping
    public List<Expense> getAllExpenses() {
        return service.getAllExpenses();
    }

    @PostMapping
    public Expense createExpense(@RequestBody Expense expense) {
        // @RequestBody means that the expense object will be populated with the data from the request body, which is expected to be in JSON format.
        return service.saveExpense(expense);
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable UUID id) {
        // @PathVariable means that the id will be extracted from the URL path.
        service.deleteExpense(id);
    }




}
