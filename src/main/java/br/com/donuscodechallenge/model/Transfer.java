package br.com.donuscodechallenge.model;

import br.com.donuscodechallenge.entities.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Transfer extends Transaction {

    private String cpfToReceiveTransfer;

    public Transfer(String cpf, BigDecimal transactionValue, TransactionType transactionType, String cpfToReceiveTransfer) {
        super(cpf, transactionValue, transactionType);
        this.cpfToReceiveTransfer = cpfToReceiveTransfer;
    }

    public String getCpfToReceiveTransfer() {
        return cpfToReceiveTransfer;
    }

    public void setCpfToReceiveTransfer(String cpfToReceiveTransfer) {
        this.cpfToReceiveTransfer = cpfToReceiveTransfer;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "cpfToReceiveTransfer='" + cpfToReceiveTransfer + '\'' +
                '}';
    }
}
