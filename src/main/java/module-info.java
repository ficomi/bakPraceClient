module pexesoclient {
    requires javafx.fxml;
    requires javafx.controls;
    requires slf4j.api;
    requires java.desktop;
    requires java.logging;

    exports UI.controllers;

    opens pexesoclient;
}