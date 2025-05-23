package co.edu.uniquindio.proyectofinalprogramacion.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.*;
import javafx.scene.control.ChoiceDialog;

public class PacienteController {

    private static final String DISPONIBILIDADES_CSV = "disponibilidades.csv";
    private static final String CITAS_CSV = "citas.csv";

    @FXML
    private javafx.scene.control.ComboBox<String> comboDisponibilidades;

    @FXML
    private void handleSolicitarCita(ActionEvent event) {
        String pacienteId = pedirDato("Solicitar Cita", "Ingrese su ID de usuario:");
        if (pacienteId == null) return;

        if (comboDisponibilidades.getItems().isEmpty()) {
            String medicoId = pedirDato("Solicitar Cita", "Ingrese el ID del médico:");
            if (medicoId == null) return;

            List<Disponibilidad> lista = cargarDisponibilidadesPorMedico(medicoId);
            if (lista.isEmpty()) {
                mostrarAlerta("Sin disponibilidad", "El médico no tiene horarios disponibles.");
                return;
            }
            ObservableList<String> opciones = FXCollections.observableArrayList();
            for (Disponibilidad d : lista) {
                opciones.add(d.toDisplayString());
            }
            comboDisponibilidades.setItems(opciones);
            mostrarAlerta("Seleccione disponibilidad", "Seleccione un horario y vuelva a presionar Solicitar Cita.");
            return;
        }

        String seleccion = comboDisponibilidades.getValue();
        if (seleccion == null) {
            mostrarAlerta("Seleccione disponibilidad", "Debe seleccionar un horario.");
            return;
        }

        Optional<Disponibilidad> disponibilidadOpt = buscarDisponibilidadPorDisplay(seleccion);
        if (!disponibilidadOpt.isPresent()) {
            mostrarAlerta("Error", "No se encontró la disponibilidad seleccionada.");
            return;
        }
        Disponibilidad disponibilidad = disponibilidadOpt.get();

        if (verificarConflictoCita(disponibilidad)) {
            mostrarAlerta("No disponible", "El médico ya tiene una cita programada en esa fecha y hora.");
            return;
        }

        if (registrarCita(pacienteId, disponibilidad)) {
            mostrarAlerta("Éxito", "Cita solicitada correctamente.");
            comboDisponibilidades.getItems().clear();
        } else {
            mostrarAlerta("Error", "No se pudo registrar la cita.");
        }
    }

    // Cancelación de cita médica
    @FXML
    private void handleCancelarCita(ActionEvent event) {
        String pacienteId = pedirDato("Cancelar Cita", "Ingrese su ID de usuario:");
        if (pacienteId == null) return;

        List<String> citas = new ArrayList<>();
        List<String[]> datosCitas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("citas.csv"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 5 && datos[1].equals(pacienteId) && datos[4].equals("Programada")) {
                    citas.add("ID: " + datos[0] + " | Médico: " + datos[2] + " | Fecha: " + datos[3]);
                    datosCitas.add(datos);
                }
            }
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo leer el archivo de citas.");
            return;
        }

        if (citas.isEmpty()) {
            mostrarAlerta("Sin citas", "No tienes citas programadas.");
            return;
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(citas.get(0), citas);
        dialog.setTitle("Cancelar Cita");
        dialog.setHeaderText("Selecciona la cita a cancelar:");
        Optional<String> result = dialog.showAndWait();
        if (!result.isPresent()) return;

        int index = citas.indexOf(result.get());
        String idCitaSeleccionada = datosCitas.get(index)[0];

        // Actualizar archivo
        StringBuilder nuevasCitas = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("citas.csv"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 5 && datos[0].equals(idCitaSeleccionada)) {
                    datos[4] = "Cancelada";
                    nuevasCitas.append(String.join(",", datos)).append("\n");
                } else {
                    nuevasCitas.append(linea).append("\n");
                }
            }
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo actualizar la cita.");
            return;
        }
        try (FileWriter writer = new FileWriter("citas.csv", false)) {
            writer.write(nuevasCitas.toString());
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo guardar la cancelación.");
            return;
        }
        mostrarAlerta("Éxito", "Cita cancelada correctamente.");
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

        StringBuilder historial = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("citas.csv"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 5 && datos[1].equals(pacienteId)) {
                    historial.append("ID: ").append(datos[0])
                            .append("\nMédico: ").append(datos[2])
                            .append("\nFecha: ").append(datos[3])
                            .append("\nEstado: ").append(datos[4])
                            .append("\n\n");
                }
            }
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo leer el historial de citas.");
            return;
        }
        mostrarAlerta("Historial de Citas", historial.length() > 0 ? historial.toString() : "No hay citas registradas.");
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

    // --- Métodos auxiliares ---

    private List<Disponibilidad> cargarDisponibilidadesPorMedico(String medicoId) {
        List<Disponibilidad> lista = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DISPONIBILIDADES_CSV))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 4 && datos[0].trim().equals(medicoId.trim())) {
                    lista.add(new Disponibilidad(datos[0].trim(), datos[1].trim(), datos[2].trim(), datos[3].trim()));
                }
            }
        } catch (IOException ignored) {}
        return lista;
    }

    private Optional<Disponibilidad> buscarDisponibilidadPorDisplay(String display) {
        try (BufferedReader reader = new BufferedReader(new FileReader(DISPONIBILIDADES_CSV))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 4) {
                    Disponibilidad d = new Disponibilidad(datos[0].trim(), datos[1].trim(), datos[2].trim(), datos[3].trim());
                    if (d.toDisplayString().equals(display)) {
                        return Optional.of(d);
                    }
                }
            }
        } catch (IOException ignored) {}
        return Optional.empty();
    }

    private boolean verificarConflictoCita(Disponibilidad d) {
        String fechaHora = d.fecha + "T" + d.horaInicio;
        try (BufferedReader reader = new BufferedReader(new FileReader(CITAS_CSV))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 5 &&
                        datos[2].trim().equals(d.medicoId) &&
                        datos[3].trim().equals(fechaHora) &&
                        datos[4].trim().equals("Programada")) {
                    return true;
                }
            }
        } catch (IOException ignored) {}
        return false;
    }

    private boolean registrarCita(String pacienteId, Disponibilidad d) {
        String idCita = pacienteId + "_" + d.medicoId + "_" + d.fecha + "_" + d.horaInicio;
        String fechaHora = d.fecha + "T" + d.horaInicio;
        try (FileWriter writer = new FileWriter(CITAS_CSV, true)) {
            writer.append(idCita).append(",")
                    .append(pacienteId).append(",")
                    .append(d.medicoId).append(",")
                    .append(fechaHora).append(",")
                    .append("Programada\n");
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // --- Clase interna para disponibilidad ---
    private static class Disponibilidad {
        String medicoId, fecha, horaInicio, horaFin;
        Disponibilidad(String m, String f, String hi, String hf) {
            medicoId = m; fecha = f; horaInicio = hi; horaFin = hf;
        }
        String toDisplayString() {
            return fecha + " " + horaInicio + " - " + horaFin;
        }
    }

    // --- Utilidades ---
    private String pedirDato(String titulo, String mensaje) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(titulo);
        dialog.setHeaderText(mensaje);
        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    @FXML
    private void handleActualizarDatos(ActionEvent event) {
        String pacienteId = pedirDato("Actualizar Datos", "Ingrese su ID de usuario:");
        if (pacienteId == null) return;

        String nuevoNombre = pedirDato("Actualizar Datos", "Ingrese su nuevo nombre:");
        if (nuevoNombre == null) return;

        String nuevoCorreo = pedirDato("Actualizar Datos", "Ingrese su nuevo correo:");
        if (nuevoCorreo == null) return;

        String nuevaContrasena = pedirDato("Actualizar Datos", "Ingrese su nueva contraseña:");
        if (nuevaContrasena == null) return;

        boolean actualizado = false;
        StringBuilder nuevosUsuarios = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("usuarios.csv"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 5 && datos[0].equals(pacienteId) && datos[4].equals("Paciente")) {
                    nuevosUsuarios.append(pacienteId).append(",")
                            .append(nuevoNombre).append(",")
                            .append(nuevoCorreo).append(",")
                            .append(nuevaContrasena).append(",")
                            .append("Paciente\n");
                    actualizado = true;
                } else {
                    nuevosUsuarios.append(linea).append("\n");
                }
            }
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo leer el archivo de usuarios.");
            return;
        }
        try (FileWriter writer = new FileWriter("usuarios.csv", false)) {
            writer.write(nuevosUsuarios.toString());
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo actualizar el usuario.");
            return;
        }
        if (actualizado) {
            mostrarAlerta("Éxito", "Datos actualizados correctamente.");
        } else {
            mostrarAlerta("Error", "No se encontró el usuario o no es paciente.");
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