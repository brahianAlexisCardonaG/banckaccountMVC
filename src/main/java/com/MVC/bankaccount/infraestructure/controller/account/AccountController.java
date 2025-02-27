package com.MVC.bankaccount.infraestructure.controller.account;

import com.MVC.bankaccount.application.service.AccountService;
import com.MVC.bankaccount.infraestructure.controller.account.request.AccountClientRequest;
import com.MVC.bankaccount.infraestructure.controller.account.response.AccountClientTransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(value="/create-account", headers = "Accept=application/json")
    public ResponseEntity<String> crearAccount(@RequestBody AccountClientRequest accountClientRequest) {
        accountService.save(accountClientRequest);
        return new ResponseEntity("La cuenta fue creada con éxito", HttpStatus.CREATED);
    }

    @GetMapping(value = "/list", headers = "Accept=application/json")
    public List<AccountClientTransactionResponse> getAll(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String accountNumber
    ){
        if (id == null && accountNumber == null) {
            return accountService.getAll();
        } else if (id != null) {
            List<AccountClientTransactionResponse> account = accountService.getProductById(id);
            return account;
        } else if (accountNumber != null) {
            return accountService.getByAccountNumber(accountNumber);
        }  else {
            return null;
        }
    }

}
