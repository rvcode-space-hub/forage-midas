package com.jpmc.midascore.controller;

import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/balance")
public class BalanceController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public Balance getBalance(@RequestParam String userId) {

        Optional<UserRecord> user = userRepository.findByName(userId);
            System.out.println("USER ID: " + userId);
        

        if (user.isPresent()) {
            return new Balance(user.get().getBalance());
        }

        return new Balance(0);
    }
}