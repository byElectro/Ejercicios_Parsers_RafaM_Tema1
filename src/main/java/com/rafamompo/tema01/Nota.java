package com.rafamompo.tema01;

public class Nota 
{
    private String asignatura;
    private double nota;

    public Nota(String asignatura, double nota) {
        this.asignatura = asignatura;
        this.nota = nota;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
    
    
}
