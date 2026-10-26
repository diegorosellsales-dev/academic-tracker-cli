package com.ujitracker.model;

public class Evaluacion {
    private String nombre;
    private double nota;
    private double porcentaje;

    public Evaluacion(String nombre, double nota, double porcentaje) {
        this.nombre = nombre;
        this.nota = nota;
        this.porcentaje = porcentaje;
    }

    //Getters y Setters
    public String getNombre(){
        return nombre;
    }

    public double getNota(){
        return nota;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setNota(double nota) {
        if (nota >= 0 && nota <= 10) {
            this.nota = nota;
        } else {
            throw new IllegalArgumentException("La nota debe estar entre 0 y 10");
        }
    }

    @Override
    public String toString() {
        return nombre + ": " + nota + " (" + porcentaje + "%)";
    }
}