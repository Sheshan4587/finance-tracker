package com.financetracker.service;

import com.financetracker.dto.ExpenseRequestDTO;
import com.financetracker.dto.ExpenseResponseDTO;
import com.financetracker.exception.ResourceNotFoundException;
import com.financetracker.model.Expense;
import com.financetracker.repository.ExpenseRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ExpenseService {

    private final ExpenseRepository repository;

    public ExpenseService(ExpenseRepository repository) {
        this.repository = repository;
    }

    // This method is for retrieving all expenses. It uses the ExpenseRepository to fetch all Expense entities from the database, then maps each Expense to an ExpenseResponseDTO and returns a list of these DTOs. The mapping is done using Java Streams, where each Expense is transformed into an ExpenseResponseDTO by extracting its properties and passing them to the DTO constructor.
    public List<ExpenseResponseDTO> getAllExpenses() {
        // This is a placeholder implementation. In a real application, you would retrieve expenses from a database.
        return repository.findAll().stream()
                .map(expense -> new ExpenseResponseDTO(
                        expense.getId(),
                        expense.getDescription(),
                        expense.getAmount(),
                        expense.getCategory(),
                        expense.getDate()
                ))
                .toList();

    }

    // This method is responsible for saving a new expense. It takes an ExpenseRequestDTO as input, validates it, and then saves it to the database using the ExpenseRepository. If the input is valid, it creates a new Expense entity, saves it, and returns an ExpenseResponseDTO with the saved expense's details. If the input is null, it throws an IllegalArgumentException.
    public ExpenseResponseDTO saveExpense(@Valid ExpenseRequestDTO expense) {
        if (expense == null) {
            throw new IllegalArgumentException("Expense cannot be null");

        } else {
            //In here, we are creating a new Expense entity using the data from the ExpenseRequestDTO. We then save this entity to the database using the repository's save method, which returns the saved entity (including any generated ID). Finally, we create and return an ExpenseResponseDTO using the properties of the saved Expense.
            Expense save = repository.save(new Expense(
                    expense.getDescription(),
                    expense.getAmount(),
                    expense.getCategory(),
                    expense.getDate()
            ));

            return new ExpenseResponseDTO(
                    save.getId(),
                    save.getDescription(),
                    save.getAmount(),
                    save.getCategory(),
                    save.getDate()
            );
        }
    }

    public ExpenseResponseDTO updateExpense(@Valid ExpenseRequestDTO expense, UUID id) {
        Expense find = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Expense not found with id " + id)
        );

        find.setDescription(expense.getDescription());
        find.setAmount(expense.getAmount());
        find.setCategory(expense.getCategory());
        find.setDate(expense.getDate());

        Expense save = repository.save(find);

        return new ExpenseResponseDTO(
                save.getId(),
                save.getDescription(),
                save.getAmount(),
                save.getCategory(),
                save.getDate()
        );


    }


    public void deleteExpense(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Expense not found" + id);
        }


    }


}
