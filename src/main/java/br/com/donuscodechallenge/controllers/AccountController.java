package br.com.donuscodechallenge.controllers;

import br.com.donuscodechallenge.entities.Account;
import br.com.donuscodechallenge.event.CreatedResourceEvent;
import br.com.donuscodechallenge.model.Transaction;
import br.com.donuscodechallenge.model.Transfer;
import br.com.donuscodechallenge.usecases.AccountUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping(value = "${api.version}/account", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountUseCase useCase;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping
    public ResponseEntity<Account> createAccount(@Valid @RequestBody Account account, HttpServletResponse response){
        LOGGER.info("Called AccountController method createAccount({})", account.toString());
        Account accountCreated = useCase.createAccount(account);
        publisher.publishEvent(new CreatedResourceEvent(this, response, accountCreated.getCpf()));

        return ResponseEntity.status(HttpStatus.CREATED).body(accountCreated);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Account> getAccount(@PathVariable String cpf) {
        LOGGER.info("Called AccountController method getAccount({})", cpf != null ? cpf.substring(0, 3) + ".***.***-**" : "");
        Account account = useCase.getAccount(cpf).orElse(null);
        return Objects.nonNull(account) ? ResponseEntity.ok(account) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public Iterable<Account> getAllAccounts() {
        LOGGER.info("Called AccountController method getAllAccounts()");
        return useCase.getAllAccount();
    }

    @PutMapping
    public ResponseEntity deposit(@Valid @RequestBody Transaction transaction, HttpServletResponse response){
        LOGGER.info("Called AccountController method deposit({})", transaction.toString());
        String cpfLocation = useCase.doDeposit(transaction);
        publisher.publishEvent(new CreatedResourceEvent(this, response, cpfLocation));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/draft")
    public ResponseEntity draft(@Valid @RequestBody Transaction transaction, HttpServletResponse response){
        LOGGER.info("Called AccountController method draft({})", transaction.toString());
        useCase.doDraft(transaction);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/transfer")
    public ResponseEntity transfer(@Valid @RequestBody Transfer transfer, HttpServletResponse response){
        LOGGER.info("Called AccountController method transfer({})", transfer.toString());
        useCase.doTransfer(transfer);
        return ResponseEntity.ok().build();
    }
}
