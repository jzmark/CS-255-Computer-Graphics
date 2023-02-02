module com.cs255 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.cs255 to javafx.fxml;
    exports com.cs255;
}