package com.example.financemanagerproject.controller;

import com.example.financemanagerproject.dto.IncomeRequestDto;
import com.example.financemanagerproject.dto.IncomeResponseDto;
import com.example.financemanagerproject.service.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/incomes")
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService incomeService;

    @GetMapping
    public List<IncomeResponseDto> getAllIncomes() {
        return incomeService.getAllIncomes();
    }

    @GetMapping("/{id}")
    public IncomeResponseDto getIncomeById(@PathVariable Long id) {
        return incomeService.getIncomeById(id);
    }

    @PostMapping
    public IncomeResponseDto createIncome(@RequestBody IncomeRequestDto incomeRequestDto) {
        return incomeService.createIncome(incomeRequestDto);
    }

    @PutMapping("/{id}")
    public IncomeResponseDto updateIncome(@PathVariable Long id, @RequestBody IncomeRequestDto incomeRequestDto) {
        return incomeService.updateIncome(id, incomeRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncome(id);
    }
}
