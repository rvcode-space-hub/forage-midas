package com.jpmc.midascore.service;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.repository.UserRepository;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.service.IncentiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@SuppressWarnings("unused")
@Service
public class TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private IncentiveService incentiveService;

    public void processTransaction(Long senderId, Long recipientId, Double amount) {

            System.out.println("PROCESS TRANSACTION CALLED 🔥");

        // ❌ invalid amount
        if (amount == null || amount <= 0)
            return;

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

// ---------------------------------------------------------------- 
        // Update balances (NO FLOAT )
        // sender.setBalance((float) (sender.getBalance() - amount));
        // recipient.setBalance((float) (recipient.getBalance() + amount));

        // userRepository.save(sender);
        // userRepository.save(recipient);

        // //  Save transaction
        // TransactionRecord record = new TransactionRecord();
        // record.setAmount(amount);
        // record.setSender(sender);
        // record.setRecipient(recipient);

        // transactionRepository.save(record);
// ---------------------------------------------------------------- 



        // Task 4 Update code
// ---------------------------------------------------------------- 
        // Create Transaction with APIs
TransactionRecord record = new TransactionRecord();
record.setAmount(amount);
record.setSender(sender);
record.setRecipient(recipient);

System.out.println("Record Create");

// Call Incentive API
double incentive = incentiveService.getIncentiveAmount(record);

record.setIncentive(incentive);

// Update balances
sender.setBalance((float)sender.getBalance() - amount);

recipient.setBalance((float)
        recipient.getBalance() + amount + incentive
);

// Save
userRepository.save(sender);
userRepository.save(recipient);
transactionRepository.save(record);

System.out.println("---- USERS STATE ----");
System.out.println("Sender: " + sender.getName());
System.out.println("Recipient: " + recipient.getName());


//DEBUG Task 4 
if (sender.getName().equalsIgnoreCase("wilbur")) {
    System.out.println("wilbur FINAL: " + sender.getBalance());
}

if (recipient.getName().equalsIgnoreCase("wilbur")) {
    System.out.println("wilbur FINAL: " + recipient.getBalance());
}







// Old Task 3 
        // if (sender.getName().equals("waldorf")) {
        //     System.out.println("WALDORF FINAL: " + sender.getBalance());
        // }

        // if (recipient.getName().equals("waldorf")) {
        //     System.out.println("WALDORF FINAL: " + recipient.getBalance());
        // }



    }
}