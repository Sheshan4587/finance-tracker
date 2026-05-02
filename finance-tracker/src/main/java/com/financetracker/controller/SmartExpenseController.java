package com.financetracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.financetracker.dto.ExpenseRequestDTO;
import com.financetracker.dto.ExpenseResponseDTO;
import com.financetracker.dto.SmartExpenseRequest;
import com.financetracker.service.ExpenseService;
import com.financetracker.service.GeminiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/smart-expense")
@CrossOrigin(origins = "http://localhost:5173")
public class SmartExpenseController {

    private final GeminiService geminiService;
    private final ExpenseService expenseService;

    public SmartExpenseController(GeminiService geminiService, ExpenseService expenseService) {
        this.geminiService = geminiService;
        this.expenseService = expenseService;
    }


    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> createSmartExpense(@RequestBody SmartExpenseRequest request) throws Exception {
        // store the json string
        String jsonString = geminiService.extractExpenseDetails(request.getSentence());

        // parse to ExpenseRequestDTO
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        ExpenseRequestDTO expenseRequest = mapper.readValue(jsonString, ExpenseRequestDTO.class);

        // save and return
        ExpenseResponseDTO saved = expenseService.saveExpense(expenseRequest);
        return ResponseEntity.status(201).body(saved);


    }
}
