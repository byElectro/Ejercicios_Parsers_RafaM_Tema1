
package com.rafamompo.tema01;

import java.util.ArrayList;


public class Pelicula implements Comparable <Pelicula>
{
    private String id;
    private String titulo;
    private String director;
    private int estreno;
    private int duracionMin;
    private ArrayList <String> generos;
    private double puntuacionImdb;
    private double puntuacionRt;

    public Pelicula(String id, String titulo, String director, int estreno, int duracionMin, ArrayList<String> generos, double puntuacionImdb, double puntuacionRt) {
        this.id = id;
        this.titulo = titulo;
        this.director = director;
        this.estreno = estreno;
        this.duracionMin = duracionMin;
        this.generos = generos;
        this.puntuacionImdb = puntuacionImdb;
        this.puntuacionRt = puntuacionRt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getEstreno() {
        return estreno;
    }

    public void setEstreno(int estreno) {
        this.estreno = estreno;
    }

    public int getDuracionMin() {
        return duracionMin;
    }

    public void setDuracionMin(int duracionMin) {
        this.duracionMin = duracionMin;
    }

    public ArrayList<String> getGeneros() {
        return generos;
    }

    public void setGeneros(ArrayList<String> generos) {
        this.generos = generos;
    }

    public double getPuntuacionImdb() {
        return puntuacionImdb;
    }

    public void setPuntuacionImdb(double puntuacionImdb) {
        this.puntuacionImdb = puntuacionImdb;
    }

    public double getPuntuacionRt() {
        return puntuacionRt;
    }

    public void setPuntuacionRt(double puntuacionRt) {
        this.puntuacionRt = puntuacionRt;
    }

    
    // Puntuación media.
    public double puntuacionMedia ()
    {
        return (this.puntuacionImdb + this.puntuacionRt/10) / 2;
    }
            
    // Este método me permite saber cuando una pelicula es mayor/menor/igual que otra basandose
    // en el criterio que se ponga dentro del compare. En este caso la puntuacion media. Compare
    // devuelve:
        // 0 si son iguales.
        // -1 si la pelicula es menor que la otra pelicula.
        // 1 si es mayor.
    @Override
    public int compareTo(Pelicula otraPelicula) {
        // Ordena las películas de mayor a menor según su puntuación media.
        return Double.compare(otraPelicula.puntuacionMedia(), this.puntuacionMedia());
    }
}
