package com.example.financemanagerproject.service.impl;

import com.example.financemanagerproject.dao.entity.OutcomeEntity;
import com.example.financemanagerproject.dao.repository.OutcomeRepository;
import com.example.financemanagerproject.dto.OutcomeRequestDto;
import com.example.financemanagerproject.dto.OutcomeResponseDto;
import com.example.financemanagerproject.exception.OutcomeNotFoundException;
import com.example.financemanagerproject.feign.BalanceFeignClient;
import com.example.financemanagerproject.mapper.OutcomeMapper;
import com.example.financemanagerproject.service.OutcomeService;
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
public class OutcomeServiceImpl implements OutcomeService {

    private final OutcomeRepository outcomeRepository;
    private final OutcomeMapper outcomeMapper;
    private final BalanceFeignClient balanceFeignClient; // FeignClient Injecting

    @Override
    public List<OutcomeResponseDto> getAllOutcomes() {
        List<OutcomeEntity> outcomeEntities = outcomeRepository.findAll();
        return outcomeEntities.stream()
                .map(outcomeMapper::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public OutcomeResponseDto getOutcomeById(Long id) {
        OutcomeEntity outcomeEntity = outcomeRepository.findById(id)
                .orElseThrow(() -> new OutcomeNotFoundException("Outcome not found with id: " + id));
        return outcomeMapper.mapToResponseDto(outcomeEntity);
    }

    @Override
    @Transactional
    public OutcomeResponseDto createOutcome(OutcomeRequestDto outcomeRequestDto) {
        OutcomeEntity outcomeEntity = outcomeMapper.mapToEntity(outcomeRequestDto);
        outcomeEntity = outcomeRepository.save(outcomeEntity);

        // Update Balance
        BigDecimal currentBalance = balanceFeignClient.getBalance();
        BigDecimal updatedBalance = currentBalance.subtract(outcomeEntity.getAmount());
        balanceFeignClient.updateBalance(updatedBalance);

        return outcomeMapper.mapToResponseDto(outcomeEntity);
    }

    @Override
    @Transactional
    public OutcomeResponseDto updateOutcome(Long id, OutcomeRequestDto outcomeRequestDto) {
        OutcomeEntity outcomeEntity = outcomeRepository.findById(id)
                .orElseThrow(() -> new OutcomeNotFoundException("Outcome not found with id: " + id));

        // Update Balance
        BigDecimal currentBalance = balanceFeignClient.getBalance();
        BigDecimal adjustedBalance = currentBalance.add(outcomeEntity.getAmount());

        // Update OutcomeEntity
        outcomeEntity.setCategory(outcomeRequestDto.getCategory());
        outcomeEntity.setAmount(outcomeRequestDto.getAmount());
        outcomeEntity.setDescription(outcomeRequestDto.getDescription());
        outcomeEntity.setDate(outcomeRequestDto.getDate());
        outcomeRepository.save(outcomeEntity);

        // Update Balance
        BigDecimal updatedBalance = adjustedBalance.subtract(outcomeEntity.getAmount());
        balanceFeignClient.updateBalance(updatedBalance);

        return outcomeMapper.mapToResponseDto(outcomeEntity);
    }

    @Override
    public void deleteOutcome(Long id) {
        OutcomeEntity outcomeEntity = outcomeRepository.findById(id)
                .orElseThrow(() -> new OutcomeNotFoundException("Outcome not found with id: " + id));

        // Update Balance
        BigDecimal currentBalance = balanceFeignClient.getBalance();
        BigDecimal updatedBalance = currentBalance.add(outcomeEntity.getAmount());
        balanceFeignClient.updateBalance(updatedBalance);

        outcomeRepository.delete(outcomeEntity);
    }
}
