package co.edu.uniquindio.proyectofinalprogramacion.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.io.*;
import java.util.Optional;

public class MedicoController {

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

    // Administración de horarios de consulta
    @FXML
    private void handleAdministrarHorarios(ActionEvent event) {
        String medicoId = pedirDato("Horarios", "Ingrese su ID de médico:");
        if (medicoId == null) return;
        String horarios = pedirDato("Horarios", "Ingrese los horarios disponibles separados por punto y coma (ej: Lunes 8-12;Martes 14-18):");
        if (horarios == null) return;

        File archivo = new File("horarios.csv");
        boolean actualizado = false;
        StringBuilder nuevosHorarios = new StringBuilder();
        try (BufferedReader reader = archivo.exists() ? new BufferedReader(new FileReader(archivo)) : null) {
            if (reader != null) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    String[] datos = linea.split(",", 2);
                    if (datos.length >= 2 && datos[0].equals(medicoId)) {
                        nuevosHorarios.append(medicoId).append(",").append(horarios).append("\n");
                        actualizado = true;
                    } else {
                        nuevosHorarios.append(linea).append("\n");
                    }
                }
            }
        } catch (IOException ignored) {}
        if (!actualizado) {
            nuevosHorarios.append(medicoId).append(",").append(horarios).append("\n");
        }
        try (FileWriter writer = new FileWriter(archivo, false)) {
            writer.write(nuevosHorarios.toString());
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo guardar el horario.");
            return;
        }
        mostrarAlerta("Éxito", "Horario actualizado.");
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
}