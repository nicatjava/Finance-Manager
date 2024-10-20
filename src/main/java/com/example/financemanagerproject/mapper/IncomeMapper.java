package com.example.financemanagerproject.mapper;

import com.example.financemanagerproject.dao.entity.IncomeEntity;
import com.example.financemanagerproject.dto.IncomeRequestDto;
import com.example.financemanagerproject.dto.IncomeResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IncomeMapper {
    IncomeResponseDto mapToResponseDto(IncomeEntity incomeEntity);
    IncomeEntity mapToEntity(IncomeRequestDto incomeRequestDto);
}
