package br.com.donuscodechallenge.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "ACCOUNT_HISTORIC")
public class AccountHistoric implements Serializable {
    private static final long serialVersionUID = 7847539592781914308L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @NotNull
    private LocalDateTime transactionDate;

    @NotNull
    private BigDecimal previousAccountBalance;

    private String observation;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "account")
    private Account account;

    public AccountHistoric() {
    }

    public AccountHistoric(Long id, TransactionType transactionType, @NotNull LocalDateTime transactionDate, @NotNull BigDecimal previousAccountBalance, String observation, @NotNull Account account) {
        this.id = id;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.previousAccountBalance = previousAccountBalance;
        this.observation = observation;
        this.account = account;
    }

    @PrePersist
    private void saveTransactionDate(){
        this.transactionDate = LocalDateTime.now();
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

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getPreviousAccountBalance() {
        return previousAccountBalance;
    }

    public void setPreviousAccountBalance(BigDecimal previousAccountBalance) {
        this.previousAccountBalance = previousAccountBalance;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
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
        return id.equals(that.id) &&
                transactionType == that.transactionType &&
                transactionDate.equals(that.transactionDate) &&
                previousAccountBalance.equals(that.previousAccountBalance) &&
                observation.equals(that.observation) &&
                account.equals(that.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transactionType, transactionDate, previousAccountBalance, observation, account);
    }

    @Override
    public String toString() {
        return "AccountHistoric{" +
                "id=" + id +
                ", transactionType=" + transactionType +
                ", transactionDate=" + transactionDate +
                ", previousAccountBalance=" + previousAccountBalance +
                ", observation='" + observation + '\'' +
                ", account=" + account +
                '}';
    }
}
