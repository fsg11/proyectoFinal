module co.edu.uniquindio.proyectofinalprogramacion {
    requires javafx.controls;
    requires javafx.fxml;

    exports co.edu.uniquindio.proyectofinalprogramacion.model;
    exports co.edu.uniquindio.proyectofinalprogramacion.controller to javafx.fxml;

    opens co.edu.uniquindio.proyectofinalprogramacion.model to javafx.fxml;
    opens co.edu.uniquindio.proyectofinalprogramacion.viewcontroller to javafx.fxml;
    opens co.edu.uniquindio.proyectofinalprogramacion.controller to javafx.fxml;
}
