package com.jmc.mazebank;

import com.jmc.mazebank.views.ViewFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        ViewFactory.getInstance().showLoginWindow();
    }
}
