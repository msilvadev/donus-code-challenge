package br.com.donuscodechallenge.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "ACCOUNT")
public class Account implements Serializable {

    private static final long serialVersionUID = -461325279594208849L;

    @Id
    private String cpf;
    @NotNull
    private String clientName;
    private BigDecimal accountBalance;

    public Account() {}

    public Account(String cpf, String clientName, BigDecimal accountBalance) {
        this.cpf = cpf;
        this.clientName = clientName;
        this.accountBalance = accountBalance;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public synchronized void doDeposit(BigDecimal valueToDeposit) {
        BigDecimal newBalance = accountBalance.add(valueToDeposit.multiply(UtilsToEntities.BONUS_PERCENTE).divide(UtilsToEntities.ONE_HUNDRED));
        this.setAccountBalance(newBalance.add(this.accountBalance));
    }

    public synchronized void doDraft(BigDecimal valueToDraft) {
        BigDecimal draftWithDiscount = valueToDraft.add(valueToDraft.multiply(UtilsToEntities.DISCOUNT_PERCENTE).divide(UtilsToEntities.ONE_HUNDRED));
        this.setAccountBalance(this.accountBalance.subtract(draftWithDiscount));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(cpf, account.cpf) &&
                Objects.equals(clientName, account.clientName) &&
                Objects.equals(accountBalance, account.accountBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf, clientName, accountBalance);
    }

    @Override
    public String toString() {
        return "Account{" +
                "cpf='" + cpf.substring(0, 3) + ".***.***-**" + '\'' +
                ", clientName='" + clientName + '\'' +
                ", accountBalance=" + accountBalance +
                '}';
    }
}
