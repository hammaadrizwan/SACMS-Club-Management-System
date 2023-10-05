module com.example.sacms {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sacms to javafx.fxml;
    exports com.example.sacms;
}