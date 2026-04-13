package com.jpmc.midascore.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jpmc.midascore.dto.Incentive;
import com.jpmc.midascore.entity.TransactionRecord;


 @RestController
public class IncentiveController {

    @PostMapping("/incentive")
    public Incentive calculate(@RequestBody TransactionRecord txn) {

        Incentive incentive = new Incentive();

        // simple logic (example)
        incentive.setAmount(txn.getAmount() * 0.1);

        return incentive;
    }
}
    

