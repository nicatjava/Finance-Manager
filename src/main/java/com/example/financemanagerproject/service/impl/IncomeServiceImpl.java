package com.example.financemanagerproject.service.impl;

import com.example.financemanagerproject.dao.entity.IncomeEntity;
import com.example.financemanagerproject.dao.repository.IncomeRepository;
import com.example.financemanagerproject.dto.IncomeRequestDto;
import com.example.financemanagerproject.dto.IncomeResponseDto;
import com.example.financemanagerproject.exception.IncomeInvalidException;
import com.example.financemanagerproject.exception.IncomeNotFoundException;
import com.example.financemanagerproject.feign.BalanceFeignClient;
import com.example.financemanagerproject.mapper.IncomeMapper;
import com.example.financemanagerproject.service.IncomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;
    private final IncomeMapper incomeMapper;
    private final BalanceFeignClient balanceFeignClient; // FeignClient Injecting

    @Override
    public List<IncomeResponseDto> getAllIncomes() {
        List<IncomeEntity> incomeEntities = incomeRepository.findAll();
        return incomeEntities.stream()
                .map(incomeMapper::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public IncomeResponseDto getIncomeById(Long id) {
        IncomeEntity incomeEntity = incomeRepository.findById(id)
                .orElseThrow(() -> new IncomeNotFoundException("Income not found with id: " + id));
        return incomeMapper.mapToResponseDto(incomeEntity);
    }

    @Override
    @Transactional
    public IncomeResponseDto createIncome(IncomeRequestDto incomeRequestDto) {
        // Check Income Amount
        if (incomeRequestDto.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IncomeInvalidException("Income amount must be greater than 0.");
        }

        IncomeEntity incomeEntity = incomeMapper.mapToEntity(incomeRequestDto);
        incomeEntity = incomeRepository.save(incomeEntity);

        // Update Balance
        BigDecimal currentBalance = balanceFeignClient.getBalance(); // Getting Balance with Feign
        BigDecimal updatedBalance = currentBalance.add(incomeEntity.getAmount());
        balanceFeignClient.updateBalance(updatedBalance);

        return incomeMapper.mapToResponseDto(incomeEntity);
    }

    @Override
    @Transactional
    public IncomeResponseDto updateIncome(Long id, IncomeRequestDto incomeRequestDto) {
        IncomeEntity incomeEntity = incomeRepository.findById(id)
                .orElseThrow(() -> new IncomeNotFoundException("Income not found with id: " + id));

        // Update Balance
        BigDecimal currentBalance = balanceFeignClient.getBalance();
        BigDecimal adjustedBalance = currentBalance.subtract(incomeEntity.getAmount());

        // Update IncomeEntity
        incomeEntity.setSource(incomeRequestDto.getSource());
        incomeEntity.setAmount(incomeRequestDto.getAmount());
        incomeEntity.setDescription(incomeRequestDto.getDescription());
        incomeEntity.setDate(incomeRequestDto.getDate());
        incomeRepository.save(incomeEntity);

        // Update Balance
        BigDecimal updatedBalance = adjustedBalance.add(incomeEntity.getAmount());
        balanceFeignClient.updateBalance(updatedBalance);

        return incomeMapper.mapToResponseDto(incomeEntity);
    }

    @Override
    public void deleteIncome(Long id) {
        IncomeEntity incomeEntity = incomeRepository.findById(id)
                .orElseThrow(() -> new IncomeNotFoundException("Income not found with id: " + id));

        // Update Balance
        BigDecimal currentBalance = balanceFeignClient.getBalance();
        BigDecimal updatedBalance = currentBalance.subtract(incomeEntity.getAmount());
        balanceFeignClient.updateBalance(updatedBalance);

        incomeRepository.delete(incomeEntity);
    }
}
