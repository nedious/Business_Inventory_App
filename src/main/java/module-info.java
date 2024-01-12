module imhoff.wgubikerecreated {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens imhoff.wgubikerecreated to javafx.fxml;
    exports imhoff.wgubikerecreated;

    opens imhoff.wgubikerecreated.controller to javafx.fxml;

    opens imhoff.wgubikerecreated.model to javafx.base;
}