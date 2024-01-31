module org.example.chatpgvserver {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;


    opens org.example.chatpgvserver to javafx.fxml;
    exports org.example.chatpgvserver;
    opens org.example.chatpgvserver.models;
    exports org.example.chatpgvserver.models;
    exports org.example.chatpgvserver.models.objects;
    opens org.example.chatpgvserver.models.objects;
}