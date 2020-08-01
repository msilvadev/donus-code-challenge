package br.com.donuscodechallenge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Deposit {
    @NotNull
    private String cpf;
    private BigDecimal depositValue;

    public Deposit(String cpf, BigDecimal depositValue) {
        this.cpf = cpf;
        this.depositValue = depositValue;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public BigDecimal getDepositValue() {
        return depositValue;
    }

    public void setDepositValue(BigDecimal depositValue) {
        this.depositValue = depositValue;
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "cpf='" + cpf + '\'' +
                ", depositValue=" + depositValue +
                '}';
    }
}
