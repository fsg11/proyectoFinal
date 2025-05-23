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
        List<String> opciones = Arrays.asList("Registrar", "Modificar", "Eliminar");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Registrar", opciones);
        dialog.setTitle("Gestión de Usuarios");
        dialog.setHeaderText("Seleccione una acción");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(accion -> {
            switch (accion) {
                case "Registrar":
                    registrarUsuario();
                    break;
                case "Modificar":
                    modificarUsuario();
                    break;
                case "Eliminar":
                    eliminarUsuario();
                    break;
            }
        });
    }

    private void registrarUsuario() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Registrar Usuario");
        dialog.setHeaderText("Ingrese los datos separados por coma:\nusuario,nombre,correo,contraseña,rol");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(datos -> {
            String[] partes = datos.split(",");
            if (partes.length == 5) {
                try (FileWriter writer = new FileWriter("usuarios.csv", true)) {
                    writer.append(datos).append("\n");
                    mostrarAlerta("Éxito", "Usuario registrado.");
                } catch (IOException e) {
                    mostrarAlerta("Error", "No se pudo registrar el usuario.");
                }
            } else {
                mostrarAlerta("Error", "Formato incorrecto.");
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
        String datos = pedirDato("Registrar Sala", "Ingrese los datos separados por coma:\nidSala,nombre,horario");
        if (datos != null && datos.split(",").length == 3) {
            try (FileWriter writer = new FileWriter("salas.csv", true)) {
                writer.append(datos).append("\n");
                mostrarAlerta("Éxito", "Sala registrada.");
            } catch (IOException e) {
                mostrarAlerta("Error", "No se pudo registrar la sala.");
            }
        } else {
            mostrarAlerta("Error", "Formato incorrecto.");
        }
    }

    private void modificarSala() {
        String idSala = pedirDato("Modificar Sala", "Ingrese el ID de la sala a modificar:");
        if (idSala == null) return;
        List<String[]> salas = leerCSV("salas.csv");
        Optional<String[]> encontrada = salas.stream().filter(s -> s[0].equals(idSala)).findFirst();
        if (encontrada.isPresent()) {
            String[] datos = encontrada.get();
            String nuevoNombre = pedirDato("Modificar", "Nombre actual: " + datos[1] + "\nNuevo nombre:");
            String nuevoHorario = pedirDato("Modificar", "Horario actual: " + datos[2] + "\nNuevo horario:");
            if (nuevoNombre != null && nuevoHorario != null) {
                datos[1] = nuevoNombre;
                datos[2] = nuevoHorario;
                guardarCSV("salas.csv", salas);
                mostrarAlerta("Éxito", "Sala modificada.");
            }
        } else {
            mostrarAlerta("Error", "Sala no encontrada.");
        }
    }

    private void eliminarSala() {
        String idSala = pedirDato("Eliminar Sala", "Ingrese el ID de la sala a eliminar:");
        if (idSala == null) return;
        List<String[]> salas = leerCSV("salas.csv");
        boolean eliminado = salas.removeIf(s -> s[0].equals(idSala));
        if (eliminado) {
            guardarCSV("salas.csv", salas);
            mostrarAlerta("Éxito", "Sala eliminada.");
        } else {
            mostrarAlerta("Error", "Sala no encontrada.");
        }
    }

    private void verSalas() {
        List<String[]> salas = leerCSV("salas.csv");
        StringBuilder sb = new StringBuilder();
        for (String[] s : salas) {
            sb.append("ID: ").append(s[0]).append(", Nombre: ").append(s[1]).append(", Horario: ").append(s[2]).append("\n");
        }
        mostrarAlerta("Salas Registradas", sb.length() > 0 ? sb.toString() : "No hay salas registradas.");
    }

    // --- MONITOREO Y ASIGNACIÓN ---
    @FXML
    private void handleMonitoreo(ActionEvent event) {
        String filtro = pedirDato("Monitorear Citas", "Ingrese ID de médico o paciente (deje vacío para ver todas):");
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("citas.csv"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 6) {
                    if (filtro == null || filtro.isEmpty() || datos[1].equals(filtro) || datos[2].equals(filtro)) {
                        sb.append("ID Cita: ").append(datos[0])
                                .append(", Paciente: ").append(datos[1])
                                .append(", Médico: ").append(datos[2])
                                .append(", FechaHora: ").append(datos[3])
                                .append(", Sala: ").append(datos[4])
                                .append(", Estado: ").append(datos[5])
                                .append("\n");
                    }
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
        // Simulación: muestra cantidad de citas médicas (lee "citas.csv" si existe)
        File archivo = new File("citas.csv");
        if (archivo.exists()) {
            List<String[]> citas = leerCSV("citas.csv");
            mostrarAlerta("Reporte de Citas", "Total de citas: " + citas.size());
        } else {
            mostrarAlerta("Reporte de Citas", "No hay citas registradas.");
        }
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
}