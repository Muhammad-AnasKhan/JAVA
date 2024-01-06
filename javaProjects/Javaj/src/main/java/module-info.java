module com.example.javaj {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javaj to javafx.fxml;
    exports com.example.javaj;
}