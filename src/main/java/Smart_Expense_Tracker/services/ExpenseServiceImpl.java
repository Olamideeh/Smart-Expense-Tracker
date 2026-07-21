// =====================================
// ExpenseServiceImpl.java
// =====================================

package Smart_Expense_Tracker.services;

import Smart_Expense_Tracker.Entity.Expense;
import Smart_Expense_Tracker.ExpenseSpecification.ExpenseSpecification;
import Smart_Expense_Tracker.dto.ExpenseMapper;
import Smart_Expense_Tracker.dto.ExpenseRequestDto;
import Smart_Expense_Tracker.dto.ExpenseResponseDto;
import Smart_Expense_Tracker.repository.ExpenseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }
    @Override
    public List<ExpenseResponseDto> getAllExpenses() {

        return expenseRepository.findAll()
                .stream()
                .map(ExpenseMapper::toResponse)
                .collect(Collectors.toList());
    }
    @Override
    public Page<ExpenseResponseDto> getExpenses(int page, int size,String sortBy,String direction,String category) {

        Sort sort = direction.equalsIgnoreCase( "desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page,size,sort);

        Page<Expense> expenses;
        if(category == null || category.isBlank())  {
            expenses = expenseRepository.findAll(pageable);
        }else {
            expenses = (Page<Expense>) expenseRepository.findByCategory(category,pageable);
        }

        return expenses.map(ExpenseMapper::toResponseDto);
    }
    @Override
    public List<ExpenseResponseDto> filterExpenses(
            String category,
            String title,
            String description,
            BigDecimal minAmount,
            BigDecimal maxAmount) {

        Specification<Expense> specification = Specification.where((Specification<Expense>) null);

        if (category != null && !category.isBlank()) {
            specification = specification.and(ExpenseSpecification.hasCategory(category));
        }

        if (title != null && !title.isBlank()) {
            specification = specification.and(ExpenseSpecification.titleContains(title));
        }

        if (description != null && !description.isBlank()) {
            specification = specification.and(ExpenseSpecification.descriptionContains(description));
        }

        if (minAmount != null) {
            specification = specification.and(ExpenseSpecification.amountGreaterThan(minAmount));
        }

        if (maxAmount != null) {
            specification = specification.and(ExpenseSpecification.amountLessThan(maxAmount));
        }

        return expenseRepository.findAll((Sort) specification)
                .stream()
                .map(ExpenseMapper::toResponseDto)
                .toList();
    }

   public static final Logger log =
           LoggerFactory.getLogger(ExpenseServiceImpl.class);

    @Override
    public ExpenseResponseDto addExpense(ExpenseRequestDto request) {

        Expense expense = ExpenseMapper.toEntity(request);

        log.info("Saving expense: {}", expense.getTitle());

        Expense savedExpense = expenseRepository.save(expense);

        log.info("Expense saved successfully with ID {}", savedExpense.getId());

        return ExpenseMapper.toResponseDto(savedExpense);
    }

    @Override
    public ExpenseResponseDto getExpenseById(Long id){
        log.info("Getting expense by ID {}", id);
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        log.info("Expense found sucessfully");
        return ExpenseMapper.toResponseDto(expense);
    }

    @Override
    public ExpenseResponseDto updateExpense(Long id, ExpenseRequestDto request) {

        log.info("Updating expense with ID {}", id);

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Expense {} not found", id);
                    return new RuntimeException("Expense not found");
                });

        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDescription(request.getDescription());

        Expense updatedExpense = expenseRepository.save(expense);

        log.info("Expense updated successfully");

        return ExpenseMapper.toResponseDto(updatedExpense);
    }

    @Override
    public void deleteExpense(Long id) {

        log.info("Deleting expense with ID {}", id);

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Expense {} not found", id);
                    return new RuntimeException("Expense not found");
                });

        expenseRepository.delete(expense);

        log.info("Expense deleted successfully");
    }
    @Override
    public List<ExpenseResponseDto> searchExpenses(String keyword) {

        log.info("Searching expenses with keyword: {}", keyword);

        List<ExpenseResponseDto> expenses = expenseRepository
                .findByTitleContainingIgnoreCase(keyword)
                .stream()
                .map(ExpenseMapper::toResponseDto)
                .collect(Collectors.toList());

        if (expenses.isEmpty()) {
            log.warn("No expenses found for keyword: {}", keyword);
        } else {
            log.info("Found {} expense(s) for keyword: {}", expenses.size(), keyword);
        }

        return expenses;
    }
    @Override
    public List<ExpenseResponseDto> getExpensesByCategory(String keyword) {

        log.info("Searching expenses by category: {}", keyword);

        List<ExpenseResponseDto> expenses = expenseRepository
                .findByCategoryContainingIgnoreCase(keyword)
                .stream()
                .map(ExpenseMapper::toResponseDto)
                .toList();

        if (expenses.isEmpty()) {
            log.warn("No expenses found in category: {}", keyword);
        } else {
            log.info("Found {} expense(s) in category: {}", expenses.size(), keyword);
        }

        return expenses;
    }
    @Override
    public List<ExpenseResponseDto> getExpenseByDescription(String keyword) {

        log.info("Searching expenses by description: {}", keyword);

        List<ExpenseResponseDto> expenses = expenseRepository
                .findByDescriptionContainingIgnoreCase(keyword)
                .stream()
                .map(ExpenseMapper::toResponseDto)
                .toList();

        if (expenses.isEmpty()) {
            log.warn("No expenses found for description: {}", keyword);
        } else {
            log.info("Found {} expense(s) for description: {}", expenses.size(), keyword);
        }

        return expenses;
    }
}