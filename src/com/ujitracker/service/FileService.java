package com.ujitracker.service;

import com.ujitracker.model.Asignatura;
import com.ujitracker.model.Evaluacion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    private static final String FILE_NAME = "academic_data.txt";

    public static void guardarDatos(List<Asignatura> asignaturas) {
        StringBuilder sb = new StringBuilder();

        for (Asignatura asig : asignaturas) {
            sb.append("ASIGNATURA:").append(asig.getNombre()).append("\n");
            for (Evaluacion eval : asig.getEvaluaciones()) {
                sb.append("EVAL:").append(eval.getNombre()).append(",").append(eval.getNota()).append(",").append(eval.getPorcentaje()).append("\n");
            }
        }

        try {
            Files.writeString(Paths.get(FILE_NAME), sb.toString());
            System.out.println("💾 Datos guardados correctamente en disco.");
        } catch (IOException e) {
            System.out.println("❌ Error al guardar los datos:" + e.getMessage());
        }
    }

    public static List<Asignatura> cargarDatos() {
        List<Asignatura> asignaturas = new ArrayList<>();
        Path ruta = Paths.get(FILE_NAME);

        if (!Files.exists(ruta)) {
            return asignaturas;
        }

        try {
            List<String> lineas = Files.readAllLines(ruta);
            Asignatura asignaturaActual = null;

            for (String linea : lineas) {
                if (linea.startsWith("ASIGNATURA:")) {
                    String nombreAsignatura = linea.substring("ASIGNATURA:".length());
                    asignaturaActual = new Asignatura(nombreAsignatura);
                    asignaturas.add(asignaturaActual);
                } else if (linea.startsWith("EVAL:") && asignaturaActual != null) {
                    String datosEval = linea.substring("EVAL:".length());
                    String[] partes = datosEval.split(",");
                    if (partes.length == 3) {
                        String nombreEval = partes[0];
                        double nota = Double.parseDouble(partes[1]);
                        double porcentaje = Double.parseDouble(partes[2]);

                        Evaluacion eval = new Evaluacion(nombreEval, nota, porcentaje);
                        asignaturaActual.agregarEvaluacion(eval);
                    }
                }
            }
            System.out.println("📂 Datos cargados correctamente desde el disco.");
        } catch (IOException e) {
            System.out.println("❌ Error al leer los datos: " + e.getMessage());
        }

        return asignaturas;
    }
}
