package com.example.financemanagerproject.service;

import com.example.financemanagerproject.dto.OutcomeRequestDto;
import com.example.financemanagerproject.dto.OutcomeResponseDto;

import java.util.List;

public interface OutcomeService {
    List<OutcomeResponseDto> getAllOutcomes();
    OutcomeResponseDto getOutcomeById(Long id);
    OutcomeResponseDto createOutcome(OutcomeRequestDto outcomeRequestDto);
    OutcomeResponseDto updateOutcome(Long id, OutcomeRequestDto outcomeRequestDto);
    void deleteOutcome(Long id);
}
