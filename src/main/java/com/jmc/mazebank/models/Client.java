package com.jmc.mazebank.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Client {
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty payeeAddress;
    private final ObjectProperty<Account> savingsAccount;
    private final ObjectProperty<Account> pensionAccount;
    private final ObjectProperty<LocalDate> dateCreated;

    public Client(String firstName, String lastName, String payeeAddress, Account savingsAccount, Account pensionAccount, LocalDate dateCreated) {
        this.firstName = new SimpleStringProperty(this, "firstName", firstName);
        this.lastName = new SimpleStringProperty(this, "lastName", lastName);
        this.payeeAddress = new SimpleStringProperty(this, "payeeAddress", payeeAddress);
        this.savingsAccount = new SimpleObjectProperty<>(this, "savingsAccount", savingsAccount);
        this.pensionAccount = new SimpleObjectProperty<>(this, "pensionAccount", pensionAccount);
        this.dateCreated = new SimpleObjectProperty<>(this, "dateCreated", dateCreated);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public StringProperty payeeAddressProperty() {
        return payeeAddress;
    }

    public ObjectProperty<Account> savingsAccountProperty() {
        return savingsAccount;
    }

    public ObjectProperty<Account> pensionAccountProperty() {
        return pensionAccount;
    }

    public ObjectProperty<LocalDate> dateCreatedProperty() {
        return dateCreated;
    }
}
