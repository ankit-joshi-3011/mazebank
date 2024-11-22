package com.jmc.mazebank.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.time.LocalDate;

public class PensionAccount extends Account {
    private final DoubleProperty withdrawalLimit;

    public PensionAccount(String owner, String accountNumber, Double balance, LocalDate dateCreated, double withdrawalLimit) {
        super(owner, accountNumber, balance, dateCreated);
        this.withdrawalLimit = new SimpleDoubleProperty(withdrawalLimit);
    }

    public DoubleProperty withdrawalLimitProperty() {
        return withdrawalLimit;
    }
}
