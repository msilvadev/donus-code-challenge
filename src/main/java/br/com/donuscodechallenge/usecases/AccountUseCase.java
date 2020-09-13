package br.com.donuscodechallenge.usecases;

import br.com.donuscodechallenge.entities.Account;
import br.com.donuscodechallenge.model.Transaction;
import br.com.donuscodechallenge.model.Transfer;
import br.com.donuscodechallenge.repositories.AccountRepository;
import br.com.donuscodechallenge.usecases.exception.AccountAlreadyExistException;
import br.com.donuscodechallenge.usecases.exception.AccountNotExistException;
import br.com.donuscodechallenge.usecases.exception.BalanceCouldNotBeNegative;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Component
public class AccountUseCase implements BankOperations{

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountUseCase.class);

    private final AccountRepository repository;

    private final AccountHistoricUseCase accountHistoricUseCase;

    @Autowired
    public AccountUseCase(AccountRepository repository,
                          AccountHistoricUseCase accountHistoricUseCase) {
        this.repository = repository;
        this.accountHistoricUseCase = accountHistoricUseCase;
    }

    public Account createAccount(Account account) {
        LOGGER.info("Called AccountUseCase method createAccount({})", account.toString() != null ? account.toString() : "");

        Account accountCreated = repository.findById(account.getCpf()).orElse(null);

        if(accountCreated != null) {
            LOGGER.error("AccountAlreadyExistException because this account({}) already exist", account.getCpf());
            throw new AccountAlreadyExistException();
        }

        return repository.save(account);
    }

    public Account getAccount(String cpf) {
        LOGGER.info("Called AccountUseCase method getAccount({})", cpf != null ? cpf.substring(0, 3) + ".***.***-**" : "");
        return repository.findById(cpf).get();
    }

    public Iterable<Account> getAllAccount() {
        LOGGER.info("Called AccountUseCase method getAccount()");
        return repository.findAll();
    }

    @Override
    public String doDeposit(Transaction transaction) {
        LOGGER.info("Called AccountUseCase method createAccount({})", transaction.toString() != null
                ? transaction.toString() : "");

        Account account = verifyAccount(transaction.getCpf());
        BigDecimal previousAccountBalance = account.getAccountBalance();

        account.doDeposit(transaction.getTransactionValue());
        repository.save(account);
        accountHistoricUseCase.saveHistoric(account, transaction, previousAccountBalance);

        return account.getCpf();
    }

    @Override
    public String doDraft(Transaction transaction) {
        LOGGER.info("Called AccountUseCase method doDraft({})", transaction.toString() != null
                ? transaction.toString() : "");

        Account account = verifyAccount(transaction.getCpf());
        BigDecimal previousAccountBalance = account.getAccountBalance();

        account.doDraft(transaction.getTransactionValue());

        if (account.getAccountBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new BalanceCouldNotBeNegative();
        } else {
            repository.save(account);
            accountHistoricUseCase.saveHistoric(account, transaction, previousAccountBalance);
        }

        return account.getCpf();
    }

    @Override
    public void doTransfer(Transfer transfer) {
        LOGGER.info("Called AccountUseCase method doTransfer({})", transfer.toString() != null
                ? transfer.toString() : "");

        Account accountFrom = verifyAccount(transfer.getCpf());
        Account accountToReceive = verifyAccount(transfer.getCpfToReceiveTransfer());

        BigDecimal previousAccountBalanceAccountTo = accountToReceive.getAccountBalance();
        BigDecimal previousAccountBalanceAccountFrom = accountFrom.getAccountBalance();

        accountFrom.setAccountBalance(accountFrom.getAccountBalance()
                .subtract(transfer.getTransactionValue()));

        if (accountFrom.getAccountBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new BalanceCouldNotBeNegative();
        } else {
            repository.save(accountFrom);
            accountToReceive.setAccountBalance(accountToReceive.getAccountBalance().add(transfer.getTransactionValue()));
            repository.save(accountToReceive);

            accountHistoricUseCase.saveHistoric(accountToReceive,
                    accountFrom,
                    transfer,
                    previousAccountBalanceAccountTo,
                    previousAccountBalanceAccountFrom);
        }
    }

    private Account verifyAccount(String cpf){
        try {
            return getAccount(cpf);
        }catch (NoSuchElementException ex){
            LOGGER.error("AccountNotExistException: {}", cpf.substring(0, 3));
            throw new AccountNotExistException(cpf, ex.getCause());
        }
    }
}
