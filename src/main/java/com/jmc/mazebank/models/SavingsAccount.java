package com.jmc.mazebank.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class SavingsAccount extends Account {
    private final IntegerProperty transactionLimit;

    public SavingsAccount(String owner, String accountNumber, Double balance, Integer transactionLimit) {
        super(owner, accountNumber, balance);
        this.transactionLimit = new SimpleIntegerProperty(this, "transactionLimit", transactionLimit);
    }

    public IntegerProperty transactionLimitProperty() {
        return transactionLimit;
    }
}
