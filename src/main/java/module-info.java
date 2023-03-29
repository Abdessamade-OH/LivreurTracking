module ma.fstt.livreur {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    exports ma.fstt.model;

    exports ma.fstt.livreur;
    opens ma.fstt.livreur to javafx.fxml;

    requires java.sql;
}