module com.jmc.mazebank {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.jmc.mazebank to javafx.fxml;
    exports com.jmc.mazebank;
}