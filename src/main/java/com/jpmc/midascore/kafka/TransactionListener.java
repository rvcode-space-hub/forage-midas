
package com.jpmc.midascore.kafka;

import com.jpmc.midascore.foundation.Transaction;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.jpmc.midascore.service.TransactionService; // new add 
import org.springframework.beans.factory.annotation.Autowired; // new add

@Component
public class TransactionListener {

    @Autowired
    private TransactionService transactionService;

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-group")
    public void listen(Transaction transaction) {

        // amount print karo
        System.out.println("Amount: " + transaction.getAmount());
        // optional debug
        System.out.println("Full Object: " + transaction);

        transactionService.processTransaction(
                Long.valueOf(transaction.getSenderId()),
                Long.valueOf(transaction.getRecipientId()),
                Double.valueOf(transaction.getAmount()));

    }
}