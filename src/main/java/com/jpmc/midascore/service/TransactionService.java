package com.jpmc.midascore.service;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.repository.UserRepository;
import com.jpmc.midascore.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public void processTransaction(Long senderId, Long recipientId, Double amount) {

        // ❌ invalid amount
        if (amount == null || amount <= 0) return;

        // 🔍 Fetch users
        Optional<UserRecord> senderOpt = userRepository.findById(senderId);
        Optional<UserRecord> recipientOpt = userRepository.findById(recipientId);

        // ❌ Validation: user exist or not
        if (senderOpt.isEmpty() || recipientOpt.isEmpty()) {
            return;
        }

        UserRecord sender = senderOpt.get();
        UserRecord recipient = recipientOpt.get();

        // ❌ Validation: sufficient balance
        if (sender.getBalance() < amount) {
            return;
        }

        // ✅ Update balances (NO FLOAT ❌)
        sender.setBalance((float) (sender.getBalance() - amount));
        recipient.setBalance((float) (recipient.getBalance() + amount));

        userRepository.save(sender);
        userRepository.save(recipient);

        // ✅ Save transaction
        TransactionRecord record = new TransactionRecord();
        record.setAmount(amount);
        record.setSender(sender);
        record.setRecipient(recipient);

        transactionRepository.save(record);

        // ✅ PRINT AFTER UPDATE (IMPORTANT 🔥)
        if (sender.getName().equals("waldorf")) {
            System.out.println("WALDORF FINAL: " + sender.getBalance());
        }

        if (recipient.getName().equals("waldorf")) {
            System.out.println("WALDORF FINAL: " + recipient.getBalance());
        }
    }
}