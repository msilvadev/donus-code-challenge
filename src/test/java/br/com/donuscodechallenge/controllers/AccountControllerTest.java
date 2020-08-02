package br.com.donuscodechallenge.controllers;

import br.com.donuscodechallenge.entities.Account;
import br.com.donuscodechallenge.entities.TransactionType;
import br.com.donuscodechallenge.model.Transaction;
import br.com.donuscodechallenge.model.Transfer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@AutoConfigureMockMvc
public class AccountControllerTest {

    @Mock
    private AccountController controller;

    private Account account;

    private final Transaction transaction = new Transaction("76268969049",
            new BigDecimal("200.00"),
            TransactionType.DEPOSIT);

    private Transfer transfer;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);

        account = new Account("76268969049", "Name", new BigDecimal("200.00"));

        transfer = new Transfer("76268969049",
                new BigDecimal("200.00"),
                TransactionType.TRANSFER,
                "88868969049");
    }

    @Test
    public void createAccountTest() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf(String.valueOf(APPLICATION_JSON)));
        ResponseEntity<Account> responseEntityMock = new ResponseEntity<>(
            account,
            header,
            HttpStatus.CREATED
        );

        when(controller.createAccount(any(Account.class), any(HttpServletResponse.class)))
                .thenReturn(responseEntityMock);

        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        ResponseEntity<Account> response = controller.createAccount(account, httpServletResponse);

        assertThat(response).isEqualTo(responseEntityMock);
    }

    @Test
    public void getAccountTest(){
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf(String.valueOf(APPLICATION_JSON)));
        ResponseEntity<Account> responseEntityMock = new ResponseEntity<>(
                account,
                header,
                HttpStatus.OK
        );

        when(controller.getAccount(any(String.class)))
                .thenReturn(responseEntityMock);

        ResponseEntity<Account> response = controller.getAccount("76268969049");

        assertThat(response).isEqualTo(responseEntityMock);
    }

    @Test
    public void notFoundGetAccountTest(){
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf(String.valueOf(APPLICATION_JSON)));
        ResponseEntity<Account> responseEntityMock = new ResponseEntity<>(
                null,
                header,
                HttpStatus.NOT_FOUND
        );

        when(controller.getAccount(any(String.class)))
                .thenReturn(responseEntityMock);

        ResponseEntity<Account> response = controller.getAccount("76268969049");

        assertThat(response).isEqualTo(responseEntityMock);
    }

    @Test
    public void getAllAccountsTest(){
        List<Account> accountList = Collections.singletonList(account);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf(String.valueOf(APPLICATION_JSON)));
        ResponseEntity<List<Account>> responseEntityMock = new ResponseEntity<>(
                accountList,
                header,
                HttpStatus.OK
        );

        when(controller.getAllAccounts())
                .thenReturn(responseEntityMock.getBody());

        Iterable<Account> response = controller.getAllAccounts();

        assertThat(response).isEqualTo(responseEntityMock.getBody());
    }

    @Test
    public void depositTest() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf(String.valueOf(APPLICATION_JSON)));
        ResponseEntity<Account> responseEntityMock = new ResponseEntity<>(
                null,
                header,
                HttpStatus.OK
        );

        when(controller.deposit(any(Transaction.class), any(HttpServletResponse.class)))
                .thenReturn(responseEntityMock);

        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        ResponseEntity response;
        response = controller.deposit(transaction, httpServletResponse);

        assertThat(response.getStatusCode()).isEqualTo(responseEntityMock.getStatusCode());
    }

    @Test
    public void draftTest() {
        transaction.setTransactionType(TransactionType.DRAFT);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf(String.valueOf(APPLICATION_JSON)));
        ResponseEntity<Account> responseEntityMock = new ResponseEntity<>(
                null,
                header,
                HttpStatus.OK
        );

        when(controller.draft(any(Transaction.class), any(HttpServletResponse.class)))
                .thenReturn(responseEntityMock);

        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        ResponseEntity<Account> response = controller.draft(transaction, httpServletResponse);

        assertThat(response.getStatusCode()).isEqualTo(responseEntityMock.getStatusCode());
    }

    @Test
    public void transferTest() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf(String.valueOf(APPLICATION_JSON)));
        ResponseEntity<Account> responseEntityMock = new ResponseEntity<>(
                null,
                header,
                HttpStatus.OK
        );

        when(controller.transfer(any(Transfer.class), any(HttpServletResponse.class)))
                .thenReturn(responseEntityMock);

        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        ResponseEntity response = controller.transfer(transfer, httpServletResponse);

        assertThat(response.getStatusCode()).isEqualTo(responseEntityMock.getStatusCode());
    }
}
