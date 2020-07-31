package br.com.donuscodechallenge.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "ACCOUNT_HISTORIC")
public class AccountHistoric implements Serializable {
    private static final long serialVersionUID = 7847539592781914308L;

    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @NotNull
    private LocalDate transactionDate;

    //uma categoria pode estar em varios lancamentos
    @NotNull
    @ManyToOne
    @JoinColumn(name = "account")
    private Account account;

    public AccountHistoric() {
    }

    public AccountHistoric(Long id, TransactionType transactionType, @NotNull LocalDate transactionDate, @NotNull Account account) {
        this.id = id;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountHistoric that = (AccountHistoric) o;
        return Objects.equals(id, that.id) &&
                transactionType == that.transactionType &&
                Objects.equals(transactionDate, that.transactionDate) &&
                Objects.equals(account, that.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transactionType, transactionDate, account);
    }

    @Override
    public String toString() {
        return "AccountHistoric{" +
                "id=" + id +
                ", transactionType=" + transactionType +
                ", transactionDate=" + transactionDate +
                ", account=" + account +
                '}';
    }
}
