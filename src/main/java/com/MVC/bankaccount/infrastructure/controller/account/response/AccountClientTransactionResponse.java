package com.MVC.bankaccount.infrastructure.controller.account.response;

import com.MVC.bankaccount.domain.enums.AccountType;
import com.MVC.bankaccount.infrastructure.controller.client.request.ClientRequest;
import com.MVC.bankaccount.infrastructure.controller.transaction.response.TransactionResponse;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
public class AccountClientTransactionResponse {

    private Long id;
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private BigDecimal balance;

    private LocalDate createdAt;

    private ClientRequest client;

    private List<TransactionResponse> transactions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public ClientRequest getClient() {
        return client;
    }

    public void setClient(ClientRequest client) {
        this.client = client;
    }

    public List<TransactionResponse> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionResponse> transactions) {
        this.transactions = transactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountClientTransactionResponse)) return false;
        AccountClientTransactionResponse that = (AccountClientTransactionResponse) o;

        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getAccountNumber(), that.getAccountNumber()) &&
                getAccountType() == that.getAccountType() &&
                Objects.equals(getBalance(), that.getBalance()) &&
                Objects.equals(getCreatedAt(), that.getCreatedAt()) &&
                Objects.equals(getClient(), that.getClient()) &&
                Objects.equals(getTransactions(), that.getTransactions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAccountNumber(), getAccountType(), getBalance(), getCreatedAt(), getClient(), getTransactions());
    }
}
