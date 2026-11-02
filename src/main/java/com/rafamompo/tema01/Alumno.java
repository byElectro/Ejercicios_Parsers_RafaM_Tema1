
package com.rafamompo.tema01;

import java.util.ArrayList;


public class Alumno 
{
    private String id;
    private String nombre;
    private boolean matriculado;
    private String fechaNacimiento;
    private ArrayList <Nota> notas;

    public Alumno(String id, String nombre, boolean matriculado, String fechaNacimiento, ArrayList<Nota> notas) {
        this.id = id;
        this.nombre = nombre;
        this.matriculado = matriculado;
        this.fechaNacimiento = fechaNacimiento;
        this.notas = notas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isMatriculado() {
        return matriculado;
    }

    public void setMatriculado(boolean matriculado) {
        this.matriculado = matriculado;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public ArrayList<Nota> getNotas() {
        return notas;
    }

    public void setNotas(ArrayList<Nota> notas) {
        this.notas = notas;
    }

    // Devuelve la nota media.
    public double notaMedia ()
    {
        double media = 0;
        for (int i = 0; i < notas.size(); i++)
        {
            media+= notas.get(0).getNota();
        }
        return media / notas.size ();
    }
    
    // Devuelve la nota máxima.
    public Nota notaMaxima ()
    {
        Nota mayor = notas.get(0);
        for (int i = 1; i < notas.size(); i++)
        {
            if (notas.get(i).getNota() > mayor.getNota())
            {
              mayor = notas.get(i);
            }
        }
        return mayor;
    }
}
