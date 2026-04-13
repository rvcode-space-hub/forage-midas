package com.jpmc.midascore.service;

import com.jpmc.midascore.dto.Incentive;
import com.jpmc.midascore.entity.TransactionRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IncentiveService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String URL = "http://localhost:8080/incentive";

    public double getIncentiveAmount(TransactionRecord txn) {

        try {
            System.out.println("📡 Calling Incentive API...");
            System.out.println("Txn Amount: " + txn.getAmount());

            Incentive response = restTemplate.postForObject(
                    URL,
                    txn,
                    Incentive.class);

            if (response != null) {
                System.out.println("✅ Incentive Received: " + response.getAmount());
                return response.getAmount();
            } else {
                System.out.println("⚠️ Incentive API returned null");
            }

        } catch (Exception e) {
            System.out.println("❌ Incentive API FAILED: " + e.getMessage());
        }

        // fallback
        return 0.0;
    }
}