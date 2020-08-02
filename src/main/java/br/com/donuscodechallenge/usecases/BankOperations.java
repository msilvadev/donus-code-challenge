package br.com.donuscodechallenge.usecases;

import br.com.donuscodechallenge.model.Transaction;
import br.com.donuscodechallenge.model.Transfer;

public interface BankOperations {
    public String doDeposit(Transaction transaction);
    public String doDraft(Transaction transaction);
    public void doTransfer(Transfer transfer);
}
