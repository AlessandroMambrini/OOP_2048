module com.example.oop_2048 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.oop_2048 to javafx.fxml;
    exports com.example.oop_2048;
}