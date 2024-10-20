package com.example.financemanagerproject.service;

import com.example.financemanagerproject.dto.IncomeRequestDto;
import com.example.financemanagerproject.dto.IncomeResponseDto;

import java.util.List;

public interface IncomeService {
    List<IncomeResponseDto> getAllIncomes();
    IncomeResponseDto getIncomeById(Long id);
    IncomeResponseDto createIncome(IncomeRequestDto incomeRequestDto);
    IncomeResponseDto updateIncome(Long id, IncomeRequestDto incomeRequestDto);
    void deleteIncome(Long id);
}
