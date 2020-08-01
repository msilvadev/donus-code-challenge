package br.com.donuscodechallenge.controllers;

import br.com.donuscodechallenge.entities.Account;
import br.com.donuscodechallenge.event.CreatedResourceEvent;
import br.com.donuscodechallenge.model.Deposit;
import br.com.donuscodechallenge.usecases.AccountUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Objects;

@RestController
@RequestMapping("/account")
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

    @PutMapping("/deposit")
    public ResponseEntity doDeposit(@Valid @RequestBody Deposit deposit, HttpServletResponse response){
        LOGGER.info("Called AccountController method doDeposit({})", deposit.toString());
        String cpfLocation = useCase.doDeposit(deposit);
        publisher.publishEvent(new CreatedResourceEvent(this, response, cpfLocation));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/test")
    public String test(){
        BigDecimal ONE_HUNDRED = new BigDecimal(100);
        BigDecimal balance = new BigDecimal("200.00");
        BigDecimal valueToDeposit = new BigDecimal("100.00");
        BigDecimal pct = new BigDecimal("00.5");

        NumberFormat nf = NumberFormat.getCurrencyInstance();
        return nf.format(balance.add(valueToDeposit.multiply(pct).divide(ONE_HUNDRED)));
    }
}
