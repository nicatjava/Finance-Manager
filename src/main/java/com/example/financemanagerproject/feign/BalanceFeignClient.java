package com.example.financemanagerproject.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@FeignClient(name = "balance-service", url = "http://localhost:8081/api/v1/balance")
public interface BalanceFeignClient {

    @GetMapping
    BigDecimal getBalance();

    @PostMapping("/update")
    void updateBalance(@RequestBody BigDecimal amount);
}
