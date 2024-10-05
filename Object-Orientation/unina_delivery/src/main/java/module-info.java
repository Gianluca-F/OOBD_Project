module unina.delivery {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;

    exports com.unina.oobd2324_37.control;
    opens com.unina.oobd2324_37.boundary to javafx.fxml;
}