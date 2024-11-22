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
    private final ObjectProperty<SavingsAccount> savingsAccount;
    private final ObjectProperty<PensionAccount> pensionAccount;
    private final ObjectProperty<LocalDate> dateCreated;

    public Client(String firstName, String lastName, String payeeAddress, SavingsAccount savingsAccount, PensionAccount pensionAccount, LocalDate dateCreated) {
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

    public ObjectProperty<SavingsAccount> savingsAccountProperty() {
        return savingsAccount;
    }

    public ObjectProperty<PensionAccount> pensionAccountProperty() {
        return pensionAccount;
    }

    public ObjectProperty<LocalDate> dateCreatedProperty() {
        return dateCreated;
    }
}
