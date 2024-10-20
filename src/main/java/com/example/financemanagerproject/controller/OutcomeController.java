package com.example.financemanagerproject.controller;

import com.example.financemanagerproject.dto.OutcomeRequestDto;
import com.example.financemanagerproject.dto.OutcomeResponseDto;
import com.example.financemanagerproject.service.OutcomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/outcomes")
@RequiredArgsConstructor
public class OutcomeController {

    private final OutcomeService outcomeService;

    @GetMapping
    public List<OutcomeResponseDto> getAllOutcomes() {
        return outcomeService.getAllOutcomes();
    }

    @GetMapping("/{id}")
    public OutcomeResponseDto getOutcomeById(@PathVariable Long id) {
        return outcomeService.getOutcomeById(id);
    }

    @PostMapping
    public OutcomeResponseDto createOutcome(@RequestBody OutcomeRequestDto outcomeRequestDto) {
        return outcomeService.createOutcome(outcomeRequestDto);
    }

    @PutMapping("/{id}")
    public OutcomeResponseDto updateOutcome(@PathVariable Long id, @RequestBody OutcomeRequestDto outcomeRequestDto) {
        return outcomeService.updateOutcome(id, outcomeRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteOutcome(@PathVariable Long id) {
        outcomeService.deleteOutcome(id);
    }
}
