package br.com.donuscodechallenge.usecases;

public interface BankOperations {
    public Long doDeposit();
    public Long doDraft();
    public Long doTransfer();
    public Double bonusToDeposit();
    public Double discountInDraft();
}
