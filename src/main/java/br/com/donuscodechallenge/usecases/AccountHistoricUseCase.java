package br.com.donuscodechallenge.usecases;

import br.com.donuscodechallenge.entities.Account;
import br.com.donuscodechallenge.entities.AccountHistoric;
import br.com.donuscodechallenge.model.Transaction;
import br.com.donuscodechallenge.model.Transfer;
import br.com.donuscodechallenge.repositories.AccountHistoricRepository;
import br.com.donuscodechallenge.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountHistoricUseCase {

    private AccountHistoricRepository repository;

    @Autowired
    public AccountHistoricUseCase(AccountHistoricRepository repository) {
        this.repository = repository;
    }

    @Async
    public void saveHistoric(Account account, Transaction transaction, BigDecimal previousAccountBalance){
        AccountHistoric accountHistoric = new AccountHistoric();
        accountHistoric.setTransactionType(transaction.getTransactionType());
        accountHistoric.setAccount(account);
        accountHistoric.setPreviousAccountBalance(previousAccountBalance);
        repository.save(accountHistoric);
    }

    @Async
    public void saveHistoric(Account accountToReceive,
                             Account accountFrom,
                             Transfer transfer,
                             BigDecimal previousAccountBalanceAccountTo,
                             BigDecimal previousAccountBalanceAccountFrom){
        AccountHistoric accountHistoricToReceive = new AccountHistoric();
        accountHistoricToReceive.setTransactionType(transfer.getTransactionType());
        accountHistoricToReceive.setAccount(accountToReceive);
        accountHistoricToReceive.setPreviousAccountBalance(previousAccountBalanceAccountTo);
        StringBuilder sbObservationToReceive = new StringBuilder();
        sbObservationToReceive.append("This account")
                .append(accountToReceive.toString())
                .append("receive tranfer from")
                .append(accountFrom.toString());
        accountHistoricToReceive.setObservation(sbObservationToReceive.toString());

        AccountHistoric accountHistoricFrom = new AccountHistoric();
        accountHistoricFrom.setTransactionType(transfer.getTransactionType());
        accountHistoricFrom.setAccount(accountFrom);
        accountHistoricFrom.setPreviousAccountBalance(previousAccountBalanceAccountFrom);
        StringBuilder sbObservationFrom = new StringBuilder();
        sbObservationFrom.append("This account")
                .append(accountFrom.toString())
                .append("send tranfer to")
                .append(accountToReceive.toString());
        accountHistoricFrom.setObservation(sbObservationFrom.toString());


        repository.save(accountHistoricToReceive);
        repository.save(accountHistoricFrom);
    }
}
