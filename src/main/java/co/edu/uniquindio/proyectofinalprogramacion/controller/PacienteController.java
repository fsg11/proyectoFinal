package co.edu.uniquindio.proyectofinalprogramacion.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.io.*;
import java.time.LocalDate;
import java.util.Optional;

public class PacienteController {

    @FXML
    private javafx.scene.control.ComboBox<String> comboDisponibilidades;
    // Registro y actualización de datos personales
    @FXML
    private void handleActualizarDatos(ActionEvent event) {
        String pacienteId = pedirDato("Actualizar Datos", "Ingrese su ID de usuario:");
        if (pacienteId == null) return;
        try {
            File archivo = new File("usuarios.csv");
            if (!archivo.exists()) archivo.createNewFile();
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            StringBuilder nuevosUsuarios = new StringBuilder();
            String linea;
            boolean actualizado = false;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 5 && datos[0].equals(pacienteId) && datos[4].equalsIgnoreCase("Paciente")) {
                    String nuevoNombre = pedirDato("Actualizar", "Nombre actual: " + datos[1] + "\nNuevo nombre:");
                    String nuevoCorreo = pedirDato("Actualizar", "Correo actual: " + datos[2] + "\nNuevo correo:");
                    String nuevaContrasena = pedirDato("Actualizar", "Contraseña actual: " + datos[3] + "\nNueva contraseña:");
                    if (nuevoNombre != null && nuevoCorreo != null && nuevaContrasena != null) {
                        nuevosUsuarios.append(pacienteId).append(",")
                                .append(nuevoNombre).append(",")
                                .append(nuevoCorreo).append(",")
                                .append(nuevaContrasena).append(",Paciente\n");
                        actualizado = true;
                    } else {
                        nuevosUsuarios.append(linea).append("\n");
                    }
                } else {
                    nuevosUsuarios.append(linea).append("\n");
                }
            }
            reader.close();
            FileWriter writer = new FileWriter(archivo, false);
            writer.write(nuevosUsuarios.toString());
            writer.close();
            if (actualizado) {
                mostrarAlerta("Éxito", "Datos actualizados.");
            } else {
                mostrarAlerta("Error", "Paciente no encontrado.");
            }
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo actualizar la información.");
        }
    }

    // Solicitud de cita médica
    @FXML
    private void handleSolicitarCita(ActionEvent event) {
        String pacienteId = pedirDato("Solicitar Cita", "Ingrese su ID de usuario:");
        if (pacienteId == null) return;

        // Si no hay elementos en el ComboBox, pedir el médico y cargar disponibilidades
        if (comboDisponibilidades.getItems().isEmpty()) {
            String medicoId = pedirDato("Solicitar Cita", "Ingrese el ID del médico:");
            if (medicoId == null) return;
            javafx.collections.ObservableList<String> disponibilidades = javafx.collections.FXCollections.observableArrayList();
            try (BufferedReader reader = new BufferedReader(new FileReader("disponibilidades.csv"))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    String[] datos = linea.split(",");
                    if (datos.length >= 4 && datos[0].equals(medicoId)) {
                        disponibilidades.add(datos[1] + " " + datos[2] + " - " + datos[3]);
                    }
                }
            } catch (IOException e) {
                mostrarAlerta("Error", "No se pudo cargar las disponibilidades.");
                return;
            }
            if (disponibilidades.isEmpty()) {
                mostrarAlerta("Sin disponibilidad", "El médico no tiene horarios disponibles.");
                return;
            }
            comboDisponibilidades.setItems(disponibilidades);
            mostrarAlerta("Seleccione disponibilidad", "Seleccione un horario y vuelva a presionar Solicitar Cita.");
            return;
        }

        // Si ya hay selección, registrar la cita
        String seleccion = comboDisponibilidades.getValue();
        if (seleccion == null) {
            mostrarAlerta("Seleccione disponibilidad", "Debe seleccionar un horario.");
            return;
        }
        // Parsear la selección: formato "YYYY-MM-DD HH:MM - HH:MM"
        String[] partes = seleccion.split(" ");
        String fecha = partes[0];
        String horaInicio = partes[1];
        String horaFin = partes[3];
        String medicoId = null;
        // Buscar el id del médico correspondiente a la disponibilidad seleccionada
        try (BufferedReader reader = new BufferedReader(new FileReader("disponibilidades.csv"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 4 && datos[1].equals(fecha) && datos[2].equals(horaInicio) && datos[3].equals(horaFin)) {
                    medicoId = datos[0];
                    break;
                }
            }
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo validar la disponibilidad.");
            return;
        }
        if (medicoId == null) {
            mostrarAlerta("Error", "No se encontró la disponibilidad seleccionada.");
            return;
        }
        String salaId = pedirDato("Solicitar Cita", "Ingrese el ID de la sala:");
        if (salaId == null) return;
        String fechaHora = fecha + "T" + horaInicio;
        // Validar que no haya otra cita en ese horario
        try (BufferedReader reader = new BufferedReader(new FileReader("citas.csv"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 6 && datos[2].equals(medicoId) && datos[3].equals(fechaHora) && datos[5].equals("Programada")) {
                    mostrarAlerta("No disponible", "El médico ya tiene una cita programada en esa fecha y hora.");
                    return;
                }
            }
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo validar la disponibilidad.");
            return;
        }
        // Registrar la cita
        String idCita = pacienteId + "_" + medicoId + "_" + fecha + "_" + horaInicio;
        try (FileWriter writer = new FileWriter("citas.csv", true)) {
            writer.append(idCita).append(",")
                    .append(pacienteId).append(",")
                    .append(medicoId).append(",")
                    .append(fechaHora).append(",")
                    .append(salaId).append(",")
                    .append("Programada\n");
            mostrarAlerta("Éxito", "Cita solicitada correctamente.");
            comboDisponibilidades.getItems().clear(); // Limpiar para futuras solicitudes
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo registrar la cita.");
        }
    }


    // Cancelación de cita médica
    @FXML
    private void handleCancelarCita(ActionEvent event) {
        String pacienteId = pedirDato("Cancelar Cita", "Ingrese su ID de usuario:");
        if (pacienteId == null) return;
        String idCita = pedirDato("Cancelar Cita", "Ingrese el ID de la cita a cancelar:");
        if (idCita == null) return;
        File archivo = new File("citas.csv");
        boolean cancelada = false;
        StringBuilder nuevasCitas = new StringBuilder();
        try (BufferedReader reader = archivo.exists() ? new BufferedReader(new FileReader(archivo)) : null) {
            if (reader != null) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    String[] datos = linea.split(",");
                    if (datos.length >= 6 && datos[0].equals(idCita) && datos[1].equals(pacienteId) && datos[5].equals("Programada")) {
                        datos[5] = "Cancelada";
                        nuevasCitas.append(String.join(",", datos)).append("\n");
                        cancelada = true;
                    } else {
                        nuevasCitas.append(linea).append("\n");
                    }
                }
            }
        } catch (IOException ignored) {}
        try (FileWriter writer = new FileWriter(archivo, false)) {
            writer.write(nuevasCitas.toString());
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cancelar la cita.");
            return;
        }
        if (cancelada) {
            mostrarAlerta("Éxito", "Cita cancelada.");
        } else {
            mostrarAlerta("Error", "Cita no encontrada o ya cancelada.");
        }
    }

    // Consulta de historial médico
    @FXML
    private void handleConsultarHistorial(ActionEvent event) {
        String pacienteId = pedirDato("Historial Médico", "Ingrese su ID de usuario:");
        if (pacienteId == null) return;
        File archivo = new File("historiales.csv");
        StringBuilder historial = new StringBuilder();
        try {
            if (!archivo.exists()) archivo.createNewFile();
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 3 && datos[0].equals(pacienteId)) {
                    historial.append("Fecha: ").append(datos[1])
                            .append("\nDiagnóstico/Tratamiento: ").append(datos[2]).append("\n\n");
                }
            }
            reader.close();
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo leer el historial.");
            return;
        }
        mostrarAlerta("Historial Médico", historial.length() > 0 ? historial.toString() : "No hay historial para este paciente.");
    }

    // Recepción de notificaciones sobre citas programadas
    @FXML
    private void handleVerNotificaciones(ActionEvent event) {
        String pacienteId = pedirDato("Notificaciones", "Ingrese su ID de usuario:");
        if (pacienteId == null) return;
        File archivo = new File("notificaciones.csv");
        StringBuilder notificaciones = new StringBuilder();
        try {
            if (!archivo.exists()) archivo.createNewFile();
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 3 && datos[1].equals(pacienteId)) {
                    notificaciones.append("Fecha: ").append(datos[0])
                            .append("\nMensaje: ").append(datos[2]).append("\n\n");
                }
            }
            reader.close();
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