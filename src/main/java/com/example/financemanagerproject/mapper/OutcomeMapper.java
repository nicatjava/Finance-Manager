package com.example.financemanagerproject.mapper;

import com.example.financemanagerproject.dao.entity.OutcomeEntity;
import com.example.financemanagerproject.dto.OutcomeRequestDto;
import com.example.financemanagerproject.dto.OutcomeResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OutcomeMapper {
    OutcomeResponseDto mapToResponseDto(OutcomeEntity outcomeEntity);
    OutcomeEntity mapToEntity(OutcomeRequestDto outcomeRequestDto);
}