package br.com.donuscodechallenge.model;

import br.com.donuscodechallenge.entities.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {
    @NotNull
    private String cpf;
    @NotNull
    private BigDecimal transactionValue;
    private TransactionType transactionType;

    public Transaction(String cpf, BigDecimal transactionValue, TransactionType transactionType) {
        this.cpf = cpf;
        this.transactionValue = transactionValue;
        this.transactionType = transactionType;
    }

    public Transaction(){}

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public BigDecimal getTransactionValue() {
            return transactionValue;
    }

    public void setTransactionValueValue(BigDecimal depositValue) {
        this.transactionValue = depositValue;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "cpf='" + cpf + '\'' +
                ", transactionValue=" + transactionValue +
                ", transactionType=" + transactionType +
                '}';
    }
}
