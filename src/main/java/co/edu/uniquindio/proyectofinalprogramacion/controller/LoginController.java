package co.edu.uniquindio.proyectofinalprogramacion.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginController {
    @FXML private TextField usuarioField;
    @FXML private PasswordField contrasenaField;

    @FXML
    private void handleLogin(ActionEvent event) {
        String usuario = usuarioField.getText();
        String contrasena = contrasenaField.getText();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            mostrarAlerta("Error", "Por favor ingresa usuario y contraseña.");
            return;
        }

        boolean encontrado = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("usuarios.csv"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 4 && datos[0].equals(usuario) && datos[3].equals(contrasena)) {
                    encontrado = true;
                    break;
                }
            }
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo leer el archivo de usuarios.");
            return;
        }

        if (encontrado) {
            mostrarAlerta("Éxito", "Inicio de sesión exitoso.");
            // Aquí puedes cargar la siguiente pantalla según el rol si lo deseas
        } else {
            mostrarAlerta("Error", "Usuario o contraseña incorrectos.");
        }
    }

    @FXML
    private void handleRegistro(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/co/edu/uniquindio/proyectofinalprogramacion/viewcontroller/Registro.fxml"));
            Stage stage = (Stage) usuarioField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}