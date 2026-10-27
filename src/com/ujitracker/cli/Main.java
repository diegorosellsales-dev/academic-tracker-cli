package com.ujitracker.cli;

import com.ujitracker.model.Asignatura;
import com.ujitracker.model.Evaluacion;
import com.ujitracker.service.FileService;

import javax.sound.midi.SysexMessage;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Asignatura> listaAsignaturas;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        listaAsignaturas = FileService.cargarDatos();

        boolean salir = false;
        System.out.println("=== BIENVENIDO A UJI ACADEMIC TRACKER ===");

        while (!salir) {
            mostrarMenu();
            int opcion = leerEntero("Selecciona una opcion: ");

            switch (opcion) {
                case 1:
                    crearAsignatura();
                    break;
                case 2:
                    añadirEvaluacionAAsingatura();
                    break;
                case 3:
                    mostrarResumen();
                    break;
                case 4:
                    eliminarAsignatura();
                    break;
                case 5:
                    eliminarEvaluacion();
                    break;
                case 6:
                    FileService.guardarDatos(listaAsignaturas);
                    salir = true;
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Añadir nueva asignatura");
        System.out.println("2. Añadir nota a una asignatura");
        System.out.println("3. Ver resumen de asignaturas");
        System.out.println("4. Eliminar una asignatura");
        System.out.println("5. Eliminar una evaluación de una asignatura");
        System.out.println("6. Salir y guardar");
    }

    private static void crearAsignatura() {
        System.out.println("Nombre de la asignatura (ej. Programación II): ");
        String nombre = scanner.nextLine();
        listaAsignaturas.add(new Asignatura(nombre));
        System.out.println("✓ Asignatura '" + nombre +"' creada correctamente.");
    }

    private static void añadirEvaluacionAAsingatura() {
        if (listaAsignaturas.isEmpty()) {
            System.out.println("⚠ Primero debes crear al menos una asignatura");
            return;
        }

        System.out.println("Selecciona la asignatura");
        for (int i = 0; i < listaAsignaturas.size(); i++) {
            System.out.println((i + 1) + ". " + listaAsignaturas.get(i).getNombre());
        }

        int index = leerEntero("Número de asignatura: ") - 1;

        if (index >= 0 && index < listaAsignaturas.size()) {
            System.out.print("Nombre de la prueba (ej. Parcial 1): ");
            String nombreEval = scanner.nextLine();

            double nota = leerDouble("Nota sacada (0-10): ");
            double porcentaje = leerDouble("Porcentaje de la nota final (ej. 30): ");

            try {
                Evaluacion eval = new Evaluacion(nombreEval, nota, porcentaje);
                listaAsignaturas.get(index).agregarEvaluacion(eval);
                System.out.println("✓ Evaluación añadida con éxito.");
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        } else {
            System.out.println("Selección no válida.");
        }
    }

        private static void mostrarResumen() {
            if (listaAsignaturas.isEmpty()) {
                System.out.println("No hay asignaturas registradas.");
                return;
            }

            System.out.println("\n=== ESTADO ACADÉMICO ===");
            for (Asignatura asig : listaAsignaturas) {
                System.out.println("\n• " + asig.getNombre());
                if (asig.getEvaluaciones().isEmpty()) {
                    System.out.println(" (Sin evaluaciones aún)");
                } else {
                    for (Evaluacion eval : asig.getEvaluaciones()) {
                        System.out.println(" -" + eval);
                    }
                    System.out.println(" => NOTA ACTUAL: " + String.format("%.2f", asig.calculaNotaFinal()));
                }
            }
        }

        private static void eliminarAsignatura() {
            if (listaAsignaturas.isEmpty()) {
                System.out.println("⚠ No hay asignaturas para eliminar");
                return;
            }

            System.out.println("Selecciona la asignatura que quieres eliminar:");
            for (int i = 0; i < listaAsignaturas.size(); i++) {
                System.out.println((i + 1) + ". " + listaAsignaturas.get(i).getNombre());
            }

            int index = leerEntero("Número de asignatura: ") - 1;

            if (index >= 0 && index < listaAsignaturas.size()) {
                Asignatura eliminada = listaAsignaturas.remove(index);
                System.out.println("🗑 Asignatura '" + eliminada.getNombre() + "' eliminada correctamente.");
            } else {
                System.out.println("❌ Selección no válida.");
            }
        }

        private static void eliminarEvaluacion() {
            if (listaAsignaturas.isEmpty()) {
                System.out.println("⚠ No hay asignaturas registradas.");
                return;
            }

            System.out.println("Selecciona la asignatura de la que quieres borrar una evaluación");
            for (int i = 0; i < listaAsignaturas.size(); i++){
                System.out.println((i + 1) + ". " + listaAsignaturas.get(i).getNombre());
            }

            int indexAsig = leerEntero("Número de asignatura: ") - 1;

            if (indexAsig >= 0 && indexAsig < listaAsignaturas.size()) {
                Asignatura asignatura = listaAsignaturas.get(indexAsig);
                List<Evaluacion> evals = asignatura.getEvaluaciones();

                if (evals.isEmpty()) {
                    System.out.println("⚠ Esta asignatura no tiene evaluaciones registradas.");
                    return;
                }

                System.out.println("Selecciona la evaluación que quieres eliminar:");
                for (int i = 0; i < evals.size(); i++) {
                    System.out.println((i + 1) + ". " + evals.get(i));
                }

                int indexEval = leerEntero("Número de evaluación: ") - 1;

                if (indexEval >= 0 && indexEval < evals.size()) {
                    Evaluacion eliminada = evals.remove(indexEval);
                    System.out.println("🗑 Evaluación '" + eliminada.getNombre() + "' eliminada correctamente.");
                } else {
                    System.out.println("❌ Selección de evaluación no válida.");
                }
            } else {
                System.out.println("❌ Selección de asignatura no válida.");
            }
        }

        private static int leerEntero(String mensaje) {
            System.out.print(mensaje);
            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, introduce un número entero válido.");
                scanner.next();
            }
            int num = scanner.nextInt();
            scanner.nextLine();
            return num;
        }

        private static double leerDouble(String mensaje) {
            System.out.print(mensaje);
            while (!scanner.hasNextDouble()) {
                System.out.println("Por favor, introduce un número válido (usa comas o puntos según tu confuguración).");
                scanner.next();
            }
            double num = scanner.nextDouble();
            scanner.nextLine();
            return num;
        }
}
