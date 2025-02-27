package com.MVC.bankaccount.infrastructure.controller.transaction;

import com.MVC.bankaccount.application.service.TransactionService;
import com.MVC.bankaccount.infrastructure.controller.transaction.request.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/create-transaction")
    public ResponseEntity<String> save(@RequestBody TransactionRequest transactionRequest
            , @RequestParam String identificationNumber
            , @RequestParam String accountNumber) {
        transactionService.save(transactionRequest,identificationNumber,accountNumber);
        return new ResponseEntity<>("Transacción exitosa", HttpStatus.CREATED);
    }

}
