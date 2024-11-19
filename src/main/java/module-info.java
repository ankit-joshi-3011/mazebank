module com.jmc.mazebank {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens com.jmc.mazebank to javafx.fxml;
    exports com.jmc.mazebank;
    exports com.jmc.mazebank.controllers;
    exports com.jmc.mazebank.controllers.admin;
    exports com.jmc.mazebank.controllers.client;
    exports com.jmc.mazebank.models;
    exports com.jmc.mazebank.views;
}