package co.edu.uniquindio.proyectofinalprogramacion.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class AdminController {

    // --- GESTIÓN DE USUARIOS ---
    @FXML
    private void handleGestionUsuarios(ActionEvent event) {
        List<String> opciones = Arrays.asList("Modificar", "Eliminar");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Modificar", opciones);
        dialog.setTitle("Gestión de Usuarios");
        dialog.setHeaderText("Seleccione una acción");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(accion -> {
            switch (accion) {
                case "Modificar":
                    modificarUsuario();
                    break;
                case "Eliminar":
                    eliminarUsuario();
                    break;
            }
        });
    }



    private void modificarUsuario() {
        String usuario = pedirDato("Modificar Usuario", "Ingrese el usuario a modificar:");
        if (usuario == null) return;
        List<String[]> usuarios = leerCSV("usuarios.csv");
        Optional<String[]> encontrado = usuarios.stream().filter(u -> u[0].equals(usuario)).findFirst();
        if (encontrado.isPresent()) {
            String[] datos = encontrado.get();
            String nuevoNombre = pedirDato("Modificar", "Nombre actual: " + datos[1] + "\nNuevo nombre:");
            String nuevoCorreo = pedirDato("Modificar", "Correo actual: " + datos[2] + "\nNuevo correo:");
            String nuevaContrasena = pedirDato("Modificar", "Contraseña actual: " + datos[3] + "\nNueva contraseña:");
            String nuevoRol = pedirDato("Modificar", "Rol actual: " + datos[4] + "\nNuevo rol:");
            if (nuevoNombre != null && nuevoCorreo != null && nuevaContrasena != null && nuevoRol != null) {
                datos[1] = nuevoNombre;
                datos[2] = nuevoCorreo;
                datos[3] = nuevaContrasena;
                datos[4] = nuevoRol;
                guardarCSV("usuarios.csv", usuarios);
                mostrarAlerta("Éxito", "Usuario modificado.");
            }
        } else {
            mostrarAlerta("Error", "Usuario no encontrado.");
        }
    }

    private void eliminarUsuario() {
        String usuario = pedirDato("Eliminar Usuario", "Ingrese el usuario a eliminar:");
        if (usuario == null) return;
        List<String[]> usuarios = leerCSV("usuarios.csv");
        boolean eliminado = usuarios.removeIf(u -> u[0].equals(usuario));
        if (eliminado) {
            guardarCSV("usuarios.csv", usuarios);
            mostrarAlerta("Éxito", "Usuario eliminado.");
        } else {
            mostrarAlerta("Error", "Usuario no encontrado.");
        }
    }

    // --- GESTIÓN DE SALAS Y HORARIOS ---
    @FXML
    private void handleGestionSalas(ActionEvent event) {
        List<String> opciones = Arrays.asList("Registrar Sala", "Modificar Sala", "Eliminar Sala", "Ver Salas");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Registrar Sala", opciones);
        dialog.setTitle("Gestión de Salas");
        dialog.setHeaderText("Seleccione una acción");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(accion -> {
            switch (accion) {
                case "Registrar Sala":
                    registrarSala();
                    break;
                case "Modificar Sala":
                    modificarSala();
                    break;
                case "Eliminar Sala":
                    eliminarSala();
                    break;
                case "Ver Salas":
                    verSalas();
                    break;
            }
        });
    }

    private void registrarSala() {
        String idSala = pedirDato("Registrar Sala", "Ingrese el ID de la sala:");
        if (idSala != null && !idSala.trim().isEmpty()) {
            try (FileWriter writer = new FileWriter("salas.csv", true)) {
                writer.append(idSala).append("\n");
                mostrarAlerta("Éxito", "Sala registrada.");
            } catch (IOException e) {
                mostrarAlerta("Error", "No se pudo registrar la sala.");
            }
        } else {
            mostrarAlerta("Error", "ID de sala inválido.");
        }
    }

    private void modificarSala() {
        mostrarAlerta("No disponible", "No es posible modificar una sala. Elimine y registre una nueva si es necesario.");
    }

    private void eliminarSala() {
        String idSala = pedirDato("Eliminar Sala", "Ingrese el ID de la sala a eliminar:");
        if (idSala == null) return;
        List<String> salas = new ArrayList<>();
        boolean eliminado = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("salas.csv"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.trim().equals(idSala.trim())) {
                    salas.add(linea);
                } else {
                    eliminado = true;
                }
            }
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo leer el archivo de salas.");
            return;
        }
        if (eliminado) {
            try (FileWriter writer = new FileWriter("salas.csv", false)) {
                for (String s : salas) {
                    writer.write(s + "\n");
                }
            } catch (IOException e) {
                mostrarAlerta("Error", "No se pudo guardar el archivo de salas.");
                return;
            }
            mostrarAlerta("Éxito", "Sala eliminada.");
        } else {
            mostrarAlerta("Error", "Sala no encontrada.");
        }
    }

    private void verSalas() {
        List<String> salas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("salas.csv"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    salas.add("ID: " + linea.trim());
                }
            }
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo leer el archivo de salas.");
            return;
        }
        mostrarAlerta("Salas Registradas", salas.isEmpty() ? "No hay salas registradas." : String.join("\n", salas));
    }

    // --- MONITOREO Y ASIGNACIÓN ---
    @FXML
    private void handleMonitoreo(ActionEvent event) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("citas.csv"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                // Ajusta el número de campos según tu formato de citas.csv
                if (datos.length >= 5) {
                    sb.append("ID Cita: ").append(datos[0])
                            .append(", Paciente: ").append(datos[1])
                            .append(", Médico: ").append(datos[2])
                            .append(", FechaHora: ").append(datos[3])
                            .append(", Estado: ").append(datos[4])
                            .append("\n");
                }
            }
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo leer el archivo de citas.");
            return;
        }
        mostrarAlerta("Citas", sb.length() > 0 ? sb.toString() : "No hay citas registradas.");
    }

    // --- GENERACIÓN DE REPORTES ---
    @FXML
    private void handleReportes(ActionEvent event) {
        File archivoCitas = new File("citas.csv");
        if (!archivoCitas.exists()) {
            mostrarAlerta("Reporte de Citas", "No hay citas registradas.");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoCitas));
             FileWriter writer = new FileWriter("reporte_citas.txt", false)) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 5) {
                    writer.write("ID Cita: " + datos[0]
                            + ", Paciente: " + datos[1]
                            + ", Médico: " + datos[2]
                            + ", FechaHora: " + datos[3]
                            + ", Estado: " + datos[4] + "\n");
                }
            }
            mostrarAlerta("Reporte de Citas", "Reporte generado en 'reporte_citas.txt'.");
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo generar el reporte.");
        }
    }

    @FXML
    private void handleVerReporteCitas(ActionEvent event) {
        File archivo = new File("reporte_citas.txt");
        if (!archivo.exists()) {
            mostrarAlerta("Reporte de Citas", "No existe el archivo de reporte.");
            return;
        }
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo leer el reporte.");
            return;
        }
        mostrarAlerta("Reporte de Citas", contenido.length() > 0 ? contenido.toString() : "El reporte está vacío.");
    }

    // --- UTILIDADES ---
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

    private List<String[]> leerCSV(String archivo) {
        List<String[]> lista = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                lista.add(linea.split(","));
            }
        } catch (IOException ignored) {}
        return lista;
    }

    private void guardarCSV(String archivo, List<String[]> datos) {
        try (FileWriter writer = new FileWriter(archivo, false)) {
            for (String[] fila : datos) {
                writer.append(String.join(",", fila)).append("\n");
            }
        } catch (IOException ignored) {}
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