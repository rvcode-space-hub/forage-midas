package com.jpmc.midascore.controller;

import com.jpmc.midascore.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")   // 👈 ADD THIS
public class TestController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/test")
    public String test() {
        transactionService.processTransaction(1L, 2L, 100.0);
        return "DONE";
    }
}