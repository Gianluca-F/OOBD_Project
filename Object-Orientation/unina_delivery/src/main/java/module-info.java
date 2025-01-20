module unina.delivery {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires mfx.core;

    exports com.unina.oobd2324_37.boundary;
    exports com.unina.oobd2324_37.control;
    exports com.unina.oobd2324_37.entity.DTO;
    opens com.unina.oobd2324_37.boundary to javafx.fxml;
}