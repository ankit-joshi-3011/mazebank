package com.jmc.mazebank.views;

import com.jmc.mazebank.controllers.admin.AdminController;
import com.jmc.mazebank.controllers.client.ClientController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {
    private static ViewFactory viewFactory;

    // Client Views
    private final ObjectProperty<ClientMenuOptions> clientSelectedMenuItem;

    private AnchorPane dashboardView;
    private AnchorPane transactionsView;
    private AnchorPane accountsView;

    // Admin Views
    private final ObjectProperty<AdminMenuOptions> adminSelectedMenuItem;

    private AnchorPane createClientView;

    private ViewFactory() {
        clientSelectedMenuItem = new SimpleObjectProperty<>();
        adminSelectedMenuItem = new SimpleObjectProperty<>();
    }

    public ObjectProperty<ClientMenuOptions> getClientSelectedMenuItem() {
        return clientSelectedMenuItem;
    }

    public static synchronized ViewFactory getInstance() {
        if (viewFactory == null) {
            viewFactory = new ViewFactory();
        }

        return viewFactory;
    }

    public AnchorPane getDashboardView() {
        if (dashboardView == null) {
            try {
                dashboardView = new FXMLLoader(getClass().getResource("/fxml/client/Dashboard.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return dashboardView;
    }

    public AnchorPane getTransactionsView() {
        if (transactionsView == null) {
            try {
                transactionsView = new FXMLLoader(getClass().getResource("/fxml/client/Transactions.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return transactionsView;
    }

    public AnchorPane getAccountsView() {
        if (accountsView == null) {
            try {
                accountsView = new FXMLLoader(getClass().getResource("/fxml/client/Accounts.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return accountsView;
    }

    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));

        createStage(loader);
    }

    public void showClientWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/Client.fxml"));

        ClientController clientController = new ClientController();
        loader.setController(clientController);

        createStage(loader);
    }

    public void showAdminWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin/Admin.fxml"));

        AdminController adminController = new AdminController();
        loader.setController(adminController);

        createStage(loader);
    }

    public ObjectProperty<AdminMenuOptions> getAdminSelectedMenuItem() {
        return adminSelectedMenuItem;
    }

    public AnchorPane getCreateClientView() {
        if (createClientView == null) {
            try {
                createClientView = new FXMLLoader(getClass().getResource("/fxml/admin/CreateClient.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return createClientView;
    }

    private void createStage(FXMLLoader loader) {
        Scene scene = null;

        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Maze Bank");
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }
}
