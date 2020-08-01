package br.com.donuscodechallenge.usecases;

import br.com.donuscodechallenge.entities.Account;
import br.com.donuscodechallenge.model.Deposit;
import br.com.donuscodechallenge.repositories.AccountRepository;
import br.com.donuscodechallenge.usecases.exception.AccountAlreadyExistException;
import br.com.donuscodechallenge.usecases.exception.AccountNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class AccountUseCase implements BankOperations{

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountUseCase.class);

    @Autowired
    private AccountRepository repository;

    @Override
    public String doDeposit(Deposit deposit) {
        LOGGER.info("Called AccountUseCase method createAccount({})", deposit.toString());

        Account account = getAccount(deposit.getCpf()).orElse(null);
        if(Objects.nonNull(account)) {
            account.doDeposit(deposit.getDepositValue());
            repository.save(account);

            //TODO: PROCESSO DE SALVAR HISTÓRICO
        } else {
            LOGGER.error("AccountNotExistException: ", deposit.getCpf().substring(0, 3));
            throw new AccountNotExistException();
        }

        return account.getCpf();
    }

    @Override
    public Long doDraft() {
        return null;
    }

    @Override
    public Long doTransfer() {
        return null;
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
        return repository.findById(cpf);
    }

    public Iterable<Account> getAllAccount() {
        LOGGER.info("Called AccountUseCase method getAccount({})");
        return repository.findAll();
    }
}
