package co.edu.uniquindio.proyectofinalprogramacion.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.FileWriter;
import java.io.IOException;

public class RegistroController {
    @FXML private TextField usuarioField;
    @FXML private TextField nombreField;
    @FXML private TextField correoField;
    @FXML private PasswordField contrasenaField;
    @FXML private ComboBox<String> rolComboBox;

    @FXML
    private void handleRegistrar(ActionEvent event) {
        String usuario = usuarioField.getText();
        String nombre = nombreField.getText();
        String correo = correoField.getText();
        String contrasena = contrasenaField.getText();
        String rol = rolComboBox.getValue();

        if (usuario.isEmpty() || nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || rol == null) {

            return;
        }

        try (FileWriter writer = new FileWriter("usuarios.csv", true)) {
            writer.append(usuario).append(",")
                    .append(nombre).append(",")
                    .append(correo).append(",")
                    .append(contrasena).append(",")
                    .append(rol).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Aquí puedes redirigir a la pantalla de login si lo deseas
    }
}