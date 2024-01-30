module org.example.chatpgvserver {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.chatpgvserver to javafx.fxml;
    exports org.example.chatpgvserver;
}