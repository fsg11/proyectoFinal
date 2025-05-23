package co.edu.uniquindio.proyectofinalprogramacion.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import java.io.*;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextInputDialog;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import javafx.scene.control.ChoiceDialog;

public class MedicoController {

    @FXML
    private DatePicker datePickerDisponibilidad;
    @FXML
    private Spinner<Integer> spinnerHoraInicio;
    @FXML
    private Spinner<Integer> spinnerMinutoInicio;
    @FXML
    private Spinner<Integer> spinnerHoraFin;
    @FXML
    private Spinner<Integer> spinnerMinutoFin;

    @FXML
    public void initialize() {
        spinnerHoraInicio.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(6, 22, 8));
        spinnerMinutoInicio.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0, 15));
        spinnerHoraFin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(6, 22, 9));
        spinnerMinutoFin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0, 15));
    }

    @FXML
    private void handleAdministrarHorarios(ActionEvent event) {
        String medicoId = pedirDato("Disponibilidad", "Ingrese su ID de médico:");
        if (medicoId == null) return;
        LocalDate fecha = datePickerDisponibilidad.getValue();
        int horaInicio = spinnerHoraInicio.getValue();
        int minutoInicio = spinnerMinutoInicio.getValue();
        int horaFin = spinnerHoraFin.getValue();
        int minutoFin = spinnerMinutoFin.getValue();
        if (fecha == null) {
            mostrarAlerta("Error", "Complete todos los campos.");
            return;
        }
        // Leer salas disponibles
        java.util.List<String> salas = new java.util.ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("salas.csv"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 1) {
                    salas.add(datos[0]);
                }
            }
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudieron cargar las salas.");
            return;
        }
        if (salas.isEmpty()) {
            mostrarAlerta("Error", "No hay salas registradas.");
            return;
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>(salas.get(0), salas);
        dialog.setTitle("Seleccionar Sala");
        dialog.setHeaderText("Seleccione la sala para la disponibilidad:");
        java.util.Optional<String> result = dialog.showAndWait();
        if (!result.isPresent()) return;
        String salaId = result.get();

        java.time.LocalTime inicio = java.time.LocalTime.of(horaInicio, minutoInicio);
        java.time.LocalTime fin = java.time.LocalTime.of(horaFin, minutoFin);
        try (FileWriter writer = new FileWriter("disponibilidades.csv", true)) {
            writer.append(medicoId).append(",")
                    .append(fecha.toString()).append(",")
                    .append(inicio.toString()).append(",")
                    .append(fin.toString()).append(",")
                    .append(salaId).append("\n");
            mostrarAlerta("Éxito", "Disponibilidad registrada.");
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo registrar la disponibilidad.");
        }
    }

    // Acceso a los historiales médicos de sus pacientes
    @FXML
    private void handleVerHistoriales(ActionEvent event) {
        String pacienteId = pedirDato("Ver Historial", "Ingrese el ID del paciente:");
        if (pacienteId == null) return;
        StringBuilder historial = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("historiales.csv"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 3 && datos[0].equals(pacienteId)) {
                    historial.append("Fecha: ").append(datos[1])
                            .append("\nDiagnóstico/Tratamiento: ").append(datos[2]).append("\n\n");
                }
            }
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo leer el historial.");
            return;
        }
        mostrarAlerta("Historial Médico", historial.length() > 0 ? historial.toString() : "No hay historial para este paciente.");
    }

    // Registro de diagnósticos y tratamientos
    @FXML
    private void handleRegistrarDiagnostico(ActionEvent event) {
        String pacienteId = pedirDato("Registrar Diagnóstico", "Ingrese el ID del paciente:");
        if (pacienteId == null) return;
        String diagnostico = pedirDato("Registrar Diagnóstico", "Ingrese el diagnóstico o tratamiento:");
        if (diagnostico == null) return;
        String fecha = java.time.LocalDate.now().toString();
        try (FileWriter writer = new FileWriter("historiales.csv", true)) {
            writer.append(pacienteId).append(",").append(fecha).append(",").append(diagnostico).append("\n");
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo registrar el diagnóstico.");
            return;
        }
        mostrarAlerta("Éxito", "Diagnóstico registrado.");
    }


    // Notificación de cambios en las citas
    @FXML
    private void handleVerNotificaciones(ActionEvent event) {
        String medicoId = pedirDato("Notificaciones", "Ingrese su ID de médico:");
        if (medicoId == null) return;
        StringBuilder notificaciones = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("notificaciones.csv"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 3 && datos[1].equals(medicoId)) {
                    notificaciones.append("Fecha: ").append(datos[0])
                            .append("\nMensaje: ").append(datos[2]).append("\n\n");
                }
            }
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo leer las notificaciones.");
            return;
        }
        mostrarAlerta("Notificaciones", notificaciones.length() > 0 ? notificaciones.toString() : "No hay notificaciones.");
    }

    // Utilidades
    private String pedirDato(String titulo, String mensaje) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(titulo);
        dialog.setHeaderText(mensaje);
        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void handleCerrarSesion(ActionEvent event) {
        try {
            javafx.scene.Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("/co/edu/uniquindio/proyectofinalprogramacion/viewcontroller/Login.fxml"));
            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.setTitle("Inicio de Sesión");
            stage.show();
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo volver al inicio de sesión.");
        }
    }

}