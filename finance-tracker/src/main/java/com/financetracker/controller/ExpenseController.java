package com.financetracker.controller;

import com.financetracker.model.Expense;
import com.financetracker.service.ExpenseService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Expense>> getAllExpenses() {
        // ResponseEntity is use for returning a response with a specific HTTP status code and body. In this case, we are returning an HTTP 200 OK status with the list of expenses in the response body.
        return ResponseEntity.ok(service.getAllExpenses());
    }

    @PostMapping
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) {
        // @RequestBody means that the expense object will be populated with the data from the request body, which is expected to be in JSON format.
        return ResponseEntity.status(201).body(service.saveExpense(expense));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable UUID id) {
        // @PathVariable means that the id will be extracted from the URL path.
        service.deleteExpense(id);
        return ResponseEntity.noContent().build(); // Return HTTP 204 No Content status to indicate that the deletion was successful and there is no content to return in the response body.
    }




}
