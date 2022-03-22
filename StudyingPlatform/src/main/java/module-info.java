module com.studyingplatform.application {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.StudyingPlatform.application to javafx.fxml;
    exports com.StudyingPlatform.application;
    exports com.StudyingPlatform.controllers;
    opens com.StudyingPlatform.controllers to javafx.fxml;
}