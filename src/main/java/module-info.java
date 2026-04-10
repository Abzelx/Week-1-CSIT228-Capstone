module com.example.week1csit228capstone {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.week1csit228capstone to javafx.fxml;
    exports com.example.week1csit228capstone;
}