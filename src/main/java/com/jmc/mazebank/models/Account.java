package com.jmc.mazebank.models;

import javafx.beans.property.*;

import java.time.LocalDate;

public abstract class Account {
    private final StringProperty owner;
    private final StringProperty accountNumber;
    private final DoubleProperty balance;
    private final ObjectProperty<LocalDate> dateCreated;

    public Account(String owner, String accountNumber, Double balance, LocalDate dateCreated) {
        this.owner = new SimpleStringProperty(this, "owner", owner);
        this.accountNumber = new SimpleStringProperty(this, "accountNumber", accountNumber);
        this.balance = new SimpleDoubleProperty(this, "balance", balance);
        this.dateCreated = new SimpleObjectProperty<>(this, "dateCreated", dateCreated);
    }

    public StringProperty ownerProperty() {
        return owner;
    }

    public StringProperty accountNumberProperty() {
        return accountNumber;
    }

    public DoubleProperty balanceProperty() {
        return balance;
    }

    public ObjectProperty<LocalDate> dateCreatedProperty() {
        return dateCreated;
    }

    public void setBalance(double amount, boolean addOrSubtract) {
        balance.set(balance.get() + (addOrSubtract ? amount : -amount));
    }
}
