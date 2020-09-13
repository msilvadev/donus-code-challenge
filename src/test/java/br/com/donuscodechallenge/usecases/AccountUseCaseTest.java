package br.com.donuscodechallenge.usecases;

import br.com.donuscodechallenge.entities.Account;
import br.com.donuscodechallenge.entities.TransactionType;
import br.com.donuscodechallenge.model.Transaction;
import br.com.donuscodechallenge.model.Transfer;
import br.com.donuscodechallenge.repositories.AccountRepository;
import br.com.donuscodechallenge.usecases.exception.AccountAlreadyExistException;
import br.com.donuscodechallenge.usecases.exception.AccountNotExistException;
import br.com.donuscodechallenge.usecases.exception.BalanceCouldNotBeNegative;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountUseCaseTest {

    @Autowired
    private AccountUseCase useCase;

    @Mock
    private AccountRepository repository;

    private Account account;

    private final Transaction transaction = new Transaction("76268969049",
            new BigDecimal("200.00"),
            TransactionType.DEPOSIT);

    private Transfer transfer;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);

        transfer = new Transfer("76268969049",
                new BigDecimal("200.00"),
                TransactionType.TRANSFER,
                "88868969049");
    }

    @Test
    public void createAccountTest(){
        account = new Account("88868969049", "Name", new BigDecimal("200.00"));
        Account createdAccount = useCase.createAccount(account);

        assertThat(createdAccount).isEqualTo(account);
    }

    @Test
    public void createAccountAlreadyExistTest(){
        account = new Account("76268969049", "Name", new BigDecimal("200.00"));
        useCase.createAccount(account);
        Assertions.assertThrows(AccountAlreadyExistException.class, () -> {
            useCase.createAccount(account);
        });
    }

    @Test
    public void getAccountTest(){
        account = new Account("39330997066", "Name", new BigDecimal("200.00"));
        useCase.createAccount(account);

        Account accountFound = useCase.getAccount("39330997066");

        assertThat(accountFound).isEqualTo(account);
    }

    @Test
    public void notFoundAccountTest(){
        Account accountFound = useCase.getAccount("45640020075");
        assertThat(accountFound).isNull();
    }

    @Test
    public void getAllAccountsTest(){
        account = new Account("31819169006", "Name", new BigDecimal("200.00"));
        useCase.createAccount(account);

        Iterable<Account> accountFound = useCase.getAllAccount();

        assertThat(accountFound).isNotEmpty();
    }

    @Test
    public void depositTest(){
        account = new Account("15509374055", "Name", new BigDecimal("100.00"));
        useCase.createAccount(account);
        Transaction transaction = new Transaction("15509374055", new BigDecimal("100.00"), TransactionType.DEPOSIT);

        useCase.doDeposit(transaction);

        Account accountFound = useCase.getAccount("15509374055");

        assertThat(accountFound.getCpf()).isEqualTo(account.getCpf());
        assertThat(accountFound.getAccountBalance()).isEqualTo(new BigDecimal("200.50"));
    }

    @Test
    public void depositInAccountWhichNotExistShouldThrowExceptionTest(){
        account = new Account("15509374055", "Name", new BigDecimal("100.00"));
        Transaction transaction = new Transaction("15509374055", new BigDecimal("100.00"), TransactionType.DEPOSIT);

        Assertions.assertThrows(AccountNotExistException.class, () -> {
            useCase.doDeposit(transaction);
        });
    }

    @Test
    public void draftTest(){
        account = new Account("92866123034", "Name", new BigDecimal("200.00"));
        useCase.createAccount(account);
        Transaction transaction = new Transaction("92866123034", new BigDecimal("100.00"), TransactionType.DRAFT);

        useCase.doDraft(transaction);

        Account accountFound = useCase.getAccount("92866123034");

        assertThat(accountFound.getCpf()).isEqualTo(account.getCpf());
        assertThat(accountFound.getAccountBalance()).isEqualTo(new BigDecimal("99.00"));
    }

    @Test
    public void draftThrowBalanceCouldNotBeNegativeExceptionTest(){
        account = new Account("88888899934", "Name", new BigDecimal("100.00"));
        useCase.createAccount(account);
        Transaction transaction = new Transaction("88888899934", new BigDecimal("200.00"), TransactionType.DRAFT);

        Assertions.assertThrows(BalanceCouldNotBeNegative.class, () -> {
            useCase.doDraft(transaction);
        });
    }

    @Test
    public void draftThrowAccountNotExistExceptionTest(){
        account = new Account("88888899939", "Name", new BigDecimal("1000.00"));
        Transaction transaction = new Transaction("88888899939", new BigDecimal("1.00"), TransactionType.DRAFT);

        Assertions.assertThrows(AccountNotExistException.class, () -> {
            useCase.doDraft(transaction);
        });
    }

    @Test
    public void TransferTest(){
        Account accountTo = new Account("92866123025", "Name", new BigDecimal("300.00"));
        Account accountFrom = new Account("92866123026", "Name", new BigDecimal("100.00"));

        useCase.createAccount(accountTo);
        useCase.createAccount(accountFrom);

        Transfer transfer = new Transfer("92866123025", new BigDecimal("100.00"), TransactionType.DRAFT, "92866123026") ;

        useCase.doTransfer(transfer);

        Account accountFoundTo = useCase.getAccount("92866123025");
        Account accountFoundFrom = useCase.getAccount("92866123026");

        assertThat(accountFoundTo.getAccountBalance()).isEqualTo(new BigDecimal("200.00"));
        assertThat(accountFoundFrom.getAccountBalance()).isEqualTo(new BigDecimal("200.00"));
    }

    @Test
    public void TransferThrowAccountNotExistExceptionTest(){
        Transfer transfer = new Transfer("92866123080", new BigDecimal("100.00"), TransactionType.DRAFT, "92866123026") ;

        Assertions.assertThrows(AccountNotExistException.class, () -> {
            useCase.doTransfer(transfer);
        });
    }

    @Test
    public void TransferThrowBalanceCouldNotBeNegativeTest(){
        Account accountTo = new Account("92866123020", "Name", new BigDecimal("300.00"));
        Account accountFrom = new Account("92866123021", "Name", new BigDecimal("100.00"));

        useCase.createAccount(accountTo);
        useCase.createAccount(accountFrom);

        Transfer transfer = new Transfer("92866123020", new BigDecimal("1000.00"), TransactionType.DRAFT, "92866123021") ;

        Assertions.assertThrows(BalanceCouldNotBeNegative.class, () -> {
            useCase.doTransfer(transfer);
        });
    }
}
