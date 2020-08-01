package br.com.donuscodechallenge.usecases;

import br.com.donuscodechallenge.entities.Account;
import br.com.donuscodechallenge.model.Deposit;

public interface BankOperations {
    public String doDeposit(Deposit deposit);
    public Long doDraft();
    public Long doTransfer();
}
