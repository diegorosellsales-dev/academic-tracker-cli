package com.ujitracker.model;

import java.util.ArrayList;
import java.util.List;

public class Asignatura {
    private String nombre;
    private List<Evaluacion> evaluaciones;

    public Asignatura(String nombre) {
        this.nombre = nombre;
        this.evaluaciones = new ArrayList<>();
    }

    //Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public List<Evaluacion> getEvaluaciones() {
        return evaluaciones;
    }

    public void agregarEvaluaciones(Evaluacion evaluacion) {
        this.evaluaciones.add(evaluacion);
    }

    public double calculaNotaFinal(){
        double notaTotal = 0;
        double porcentajeAcumulado = 0;

        for (Evaluacion eval : evaluaciones) {
            notaTotal += eval.getNota() * (eval.getPorcentaje() / 100.0);
            porcentajeAcumulado += eval.getPorcentaje();
        }

        return notaTotal;
    }

    @Override
    public String toString() {
        return nombre + " - Nota acumulada: " + String.format("%.2f", calculaNotaFinal());
    }
}
