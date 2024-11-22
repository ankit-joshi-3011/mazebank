package com.jmc.mazebank.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.time.LocalDate;

public class SavingsAccount extends Account {
    private final IntegerProperty transactionLimit;

    public SavingsAccount(String owner, String accountNumber, Double balance, LocalDate dateCreated, Integer transactionLimit) {
        super(owner, accountNumber, balance, dateCreated);
        this.transactionLimit = new SimpleIntegerProperty(this, "transactionLimit", transactionLimit);
    }

    public IntegerProperty transactionLimitProperty() {
        return transactionLimit;
    }

    @Override
    public String toString() {
        return accountNumberProperty().get();
    }
}
