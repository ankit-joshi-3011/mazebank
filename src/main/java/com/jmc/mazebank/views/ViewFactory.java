package com.jmc.mazebank.views;

import com.jmc.mazebank.controllers.admin.AdminController;
import com.jmc.mazebank.controllers.client.ClientController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {
    private static ViewFactory viewFactory;
    // Client Views
    private final ObjectProperty<ClientMenuOptions> clientSelectedMenuItem;
    // Admin Views
    private final ObjectProperty<AdminMenuOptions> adminSelectedMenuItem;
    private AccountType loginAccountType;

    private ViewFactory() {
        loginAccountType = AccountType.CLIENT;
        clientSelectedMenuItem = new SimpleObjectProperty<>();
        adminSelectedMenuItem = new SimpleObjectProperty<>();
    }

    public static synchronized ViewFactory getInstance() {
        if (viewFactory == null) {
            viewFactory = new ViewFactory();
        }

        return viewFactory;
    }

    public ObjectProperty<ClientMenuOptions> getClientSelectedMenuItem() {
        return clientSelectedMenuItem;
    }

    public AccountType getLoginAccountType() {
        return loginAccountType;
    }

    public void setLoginAccountType(AccountType loginAccountType) {
        this.loginAccountType = loginAccountType;
    }

    private AnchorPane getAnchorPaneFromFXML(boolean clientOrAdmin, String fxmlFileNameWithoutExtension) {
        AnchorPane anchorPane = null;
        try {
            anchorPane = new FXMLLoader(getClass().getResource(String.format("/fxml/%s/%s.fxml", (clientOrAdmin ? "client" : "admin"), fxmlFileNameWithoutExtension))).load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return anchorPane;
    }

    public AnchorPane getDashboardView() {
        return getAnchorPaneFromFXML(true, "Dashboard");
    }

    public AnchorPane getTransactionsView() {
        return getAnchorPaneFromFXML(true, "Transactions");
    }

    public AnchorPane getAccountsView() {
        return getAnchorPaneFromFXML(true, "Accounts");
    }

    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));

        createStage(loader);
    }

    public void showMessageWindow(String senderPayeeAddress, String message) {
        StackPane pane = new StackPane();

        HBox hBox = new HBox(5);
        hBox.setAlignment(Pos.CENTER);

        Label senderPayeeAddressLabel = new Label(senderPayeeAddress);
        Label messageLabel = new Label(message);

        hBox.getChildren().addAll(senderPayeeAddressLabel, messageLabel);

        pane.getChildren().add(hBox);

        Scene scene = new Scene(pane, 300, 100);

        Stage stage = new Stage();
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/images/icon.png"))));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Message");
        stage.setScene(scene);

        stage.show();
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
        return getAnchorPaneFromFXML(false, "CreateClient");
    }

    public AnchorPane getClientsView() {
        return getAnchorPaneFromFXML(false, "Clients");
    }

    public AnchorPane getDepositView() {
        return getAnchorPaneFromFXML(false, "Deposit");
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
        stage.getIcons().addAll(new Image(String.valueOf(getClass().getResource("/images/icon.png"))));
        stage.setResizable(false);
        stage.setTitle("Maze Bank");
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }
}
