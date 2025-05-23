package co.edu.uniquindio.proyectofinalprogramacion.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class PacienteController {

    @FXML
    private void handleActualizarDatos(ActionEvent event) {
        // Lógica para actualizar datos personales del paciente
        mostrarAlerta("Actualizar Datos", "Funcionalidad para actualizar datos personales.");
    }

    @FXML
    private void handleSolicitarCita(ActionEvent event) {
        // Lógica para solicitar una cita médica
        mostrarAlerta("Solicitar Cita", "Funcionalidad para solicitar cita médica.");
    }

    @FXML
    private void handleCancelarCita(ActionEvent event) {
        // Lógica para cancelar una cita médica
        mostrarAlerta("Cancelar Cita", "Funcionalidad para cancelar cita médica.");
    }

    @FXML
    private void handleConsultarHistorial(ActionEvent event) {
        // Lógica para consultar el historial médico del paciente
        mostrarAlerta("Historial Médico", "Funcionalidad para consultar historial médico.");
    }

    @FXML
    private void handleVerNotificaciones(ActionEvent event) {
        // Lógica para ver notificaciones de citas programadas
        mostrarAlerta("Notificaciones", "Funcionalidad para ver notificaciones de citas.");
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}