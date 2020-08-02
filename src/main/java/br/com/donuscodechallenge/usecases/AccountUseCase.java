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
import java.util.Objects;
import java.util.Optional;

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
        LOGGER.info("Called AccountUseCase method createAccount({})", account.toString());

        Account accountCreated = repository.findById(account.getCpf()).orElse(null);

        if(accountCreated != null) {
            LOGGER.error("AccountAlreadyExistException because this account({}) already exist", account.getCpf());
            throw new AccountAlreadyExistException();
        }

        return repository.save(account);
    }

    public Optional<Account> getAccount(String cpf) {
        LOGGER.info("Called AccountUseCase method getAccount({})", cpf != null ? cpf.substring(0, 3) + ".***.***-**" : "");
        return repository.findById(cpf != null ? cpf : "");
    }

    public Iterable<Account> getAllAccount() {
        LOGGER.info("Called AccountUseCase method getAccount()");
        return repository.findAll();
    }

    @Override
    public String doDeposit(Transaction transaction) {
        LOGGER.info("Called AccountUseCase method createAccount({})", transaction.toString());

        Account account = getAccount(transaction.getCpf()).orElse(null);
        if(Objects.nonNull(account)) {
            BigDecimal previousAccountBalance = account.getAccountBalance();

            account.doDeposit(transaction.getTransactionValue());
            repository.save(account);

            accountHistoricUseCase.saveHistoric(account, transaction, previousAccountBalance);
        } else {
            LOGGER.error("AccountNotExistException: {}", transaction.getCpf().substring(0, 3));
            throw new AccountNotExistException();
        }

        return account.getCpf();
    }

    @Override
    public String doDraft(Transaction transaction) {
        LOGGER.info("Called AccountUseCase method doDraft({})", transaction.toString());

        Account account = getAccount(transaction.getCpf()).orElse(null);

        if(Objects.nonNull(account)) {
            BigDecimal previousAccountBalance = account.getAccountBalance();

            account.doDraft(transaction.getTransactionValue());
            if(account.getAccountBalance().compareTo(BigDecimal.ZERO) < 0 ){
                throw new BalanceCouldNotBeNegative();
            } else{
                repository.save(account);

                accountHistoricUseCase.saveHistoric(account, transaction, previousAccountBalance);
            }
        } else {
            LOGGER.error("AccountNotExistException: {}", transaction.getCpf().substring(0, 3));
            throw new AccountNotExistException();
        }

        return account.getCpf();
    }

    @Override
    public void doTransfer(Transfer transfer) {
        LOGGER.info("Called AccountUseCase method doTransfer({})", transfer.toString());

        Account accountFrom = getAccount(transfer.getCpf()).orElse(null);
        Account accountToReceive = getAccount(transfer.getCpfToReceiveTransfer()).orElse(null);

        BigDecimal previousAccountBalanceAccountTo = accountToReceive != null ? accountToReceive.getAccountBalance() : new BigDecimal("0.00");
        BigDecimal previousAccountBalanceAccountFrom = accountFrom != null ? accountFrom.getAccountBalance() : new BigDecimal("0.00");

        if(Objects.nonNull(accountFrom) || Objects.nonNull(accountToReceive)) {
            if (accountFrom != null) {
                accountFrom.setAccountBalance(accountFrom.getAccountBalance()
                        .subtract(transfer.getTransactionValue()));
            } else throw new AccountNotExistException();

            if(accountFrom.getAccountBalance().compareTo(BigDecimal.ZERO) < 0 ){
                throw new BalanceCouldNotBeNegative();
            } else{
                repository.save(accountFrom);
                accountToReceive.setAccountBalance(accountToReceive.getAccountBalance().add(transfer.getTransactionValue()));
                repository.save(accountToReceive);

                accountHistoricUseCase.saveHistoric(accountToReceive,
                        accountFrom,
                        transfer,
                        previousAccountBalanceAccountTo,
                        previousAccountBalanceAccountFrom);
            }
        } else {
            LOGGER.error("AccountNotExistException: {}", accountFrom != null ? accountFrom.getCpf().substring(0, 3) : "");
            LOGGER.error("AccountNotExistException: {}", accountToReceive != null ? accountToReceive.getCpf().substring(0, 3) : "");
            throw new AccountNotExistException();
        }
    }
}
