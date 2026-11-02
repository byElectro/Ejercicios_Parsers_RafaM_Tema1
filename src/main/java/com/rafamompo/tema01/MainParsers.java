package com.rafamompo.tema01;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
//Esto es para el uso de la funcion abs del ejercicio 4 para comparar 2 numeros reales.
import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.json.JSONTokener;
import org.json.JSONArray;
import org.json.JSONObject;




public class MainParsers {

    static void main(String[] args)
    {
        //Ejercicio1 ();
        //Ejercicio2 ();
        //Ejercicio3 ();
        //Ejercicio4 ();
        //Ejercicio5 ();
        //Ejercicio6 ();
        // Ejercicio7 ();
        Ejercicio8 ();
        
    }

    
    ////////////////////////////////////////////////////////////////////////////
    // 1. Listado simple de empleados
    //
    // Cargar el dataset empleados.xml con DOM y mostrar el id, nombre y 
    // salario de cada uno de los empleados.
    ////////////////////////////////////////////////////////////////////////////

    public static void Ejercicio1 ()
    {
        FileInputStream fis;
        try 
        { 
            fis = new FileInputStream("C:\\2DAM\\AccesoaDatos\\Datasets-20261023\\empleados.xml");
        
            // Obtenemos el DocumentBuilderFactory necesario para parsear mediante DOM.
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        
            // Obtenemos el DocumentBuilder necesario para parsear mediante DOM.
            DocumentBuilder builder = factory.newDocumentBuilder();
        
            // Obtenemos el Document parseando mediante DOM.
            Document dom = builder.parse(fis);
        
            // Eliminamos nodos de texto redundantes (como saltos de línea o espacios).
            dom.getDocumentElement().normalize();
            
            // Obtenemos la lista de nodos con el tag "empleado".
            NodeList items = dom.getElementsByTagName("empleado");
                                                                                                                                                      
            // Recorremos cada uno de los nodos.
            for (int i = 0; i < items.getLength(); i++) 
            {
                // Obtenemos el nodo (empleado) de la posición i.
                // Hay que hacer un conversion de cada nodo de la lista para asi poder acceder al contenido del elemento.
                Element e = (Element) (items.item(i));
                
                // Mostramos el atributo "id".
                System.out.println ("EMPLEADO");
                System.out.println ("Id: " + e.getAttributes().getNamedItem("id").getNodeValue());
                
                // Mostrar el valor del elemento "nombre".
                System.out.println ("Nombre: " + e.getElementsByTagName("nombre").item(0).getTextContent());

                // Mostrar el valor del elemento "salario".
                System.out.println ("Salario: " + e.getElementsByTagName("salario").item(0).getTextContent() + "\n");
            }
        }
        catch (IOException ex)
        {
            System.out.println (ex.getMessage());
        }
        catch (ParserConfigurationException ex)
        {
            System.out.println (ex.getMessage());
        }
        catch (SAXException ex)
        {
            System.out.println (ex.getMessage());
        }
    
    }


    ////////////////////////////////////////////////////////////////////////////
    // 2. Objetos y estadísticas por departamento
    //
    // Mapear cada elemento <empleado> del dataset empleados.xml 
    // a una clase Empleado y calcular el salario medio por departamento.
    // Salida: mostrar en 3 columnas: 
    // Departamento | Nº empleados | Salario medio.    
    ////////////////////////////////////////////////////////////////////////////

    public static void Ejercicio2 ()
    {
        FileInputStream fis;
        
        // Lista de empleados.
        ArrayList <Empleado> empleados = new ArrayList <>();
        try 
        { 
            fis = new FileInputStream("C:\\2DAM\\AccesoaDatos\\Datasets-20261023\\empleados.xml");
        
            // Obtenemos el DocumentBuilderFactory necesario para parsear mediante DOM.
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        
            // Obtenemos el DocumentBuilder necesario para parsear mediante DOM.
            DocumentBuilder builder = factory.newDocumentBuilder();
        
            // Obtenemos el Document parseando mediante DOM.
            Document dom = builder.parse(fis);
        
            // Eliminamos nodos de texto redundantes (como saltos de línea o espacios).
            dom.getDocumentElement().normalize();
            
            // Obtenemos la lista de nodos con el tag "empleado".
            NodeList items = dom.getElementsByTagName("empleado");
                           
            String id, nombre, departamento, moneda, fechaAlta;
            double salario;
            
            // Recorremos cada uno de los nodos.
            for (int i = 0; i < items.getLength(); i++) 
            {
                // Obtenemos el nodo (empleado) de la posición i.
                Element e = (Element) (items.item(i));                
                
                // Valor del atributo "id".
                id = e.getAttributes().getNamedItem("id").getNodeValue();
                
                // Valor del elemento "nombre".
                nombre = e.getElementsByTagName("nombre").item(0).getTextContent();

                // Valor del elemento "departamento".
                departamento = e.getElementsByTagName("departamento").item(0).getTextContent();
                
                // Valor del elemento "salario".
                Element elementoSalario = (Element) (e.getElementsByTagName("salario").item(0));
                salario = Double.parseDouble (elementoSalario.getTextContent());
                
                // Valor del atributo "moneda".
                moneda = elementoSalario.getAttributes().getNamedItem("moneda").getNodeValue();
                
                // Valor del elemento "fechaAlta".
                fechaAlta = e.getElementsByTagName("fechaAlta").item(0).getTextContent();
                
                // Creación del empleado e inserción en la lista de empleados.
                empleados.add (new Empleado (id, nombre, departamento, salario, moneda, fechaAlta));
            }                         
        }
        catch (IOException ex)
        {
            System.out.println (ex.getMessage());
        }
        catch (ParserConfigurationException ex)
        {
            System.out.println (ex.getMessage());
        }
        catch (SAXException ex)
        {
            System.out.println (ex.getMessage());
        }
        
        // Mostrar resultados.
        mostrarDatosEmpleados (empleados);        
    }
    
    
    // Recorre la lista de empleados y muestra los datos en pantalla.
    public static void mostrarDatosEmpleados (ArrayList <Empleado> empleados)
    {
        Map <String, Integer> numEmpleadosPorDpto = new HashMap <>();
        Map <String, Double> salariosPorDpto = new HashMap <>();
        
        // Para cada empleado...
        for (Empleado e : empleados)
        {
            // Actualizar el número de empleados de su departamento.
            // El metodo "containsKey" es para saber si ya existe una entrada del Mapa con esa clave.
            if (numEmpleadosPorDpto.containsKey(e.getDepartamento()))
            {
                numEmpleadosPorDpto.put (e.getDepartamento(),
                                         numEmpleadosPorDpto.get (e.getDepartamento()) + 1);
            }
            else
            {
                numEmpleadosPorDpto.put (e.getDepartamento(), 1);
            }

            // Actualizar el salario total.
            if (salariosPorDpto.containsKey(e.getDepartamento()))
            {
                salariosPorDpto.put (e.getDepartamento(),
                                     salariosPorDpto.get (e.getDepartamento()) +  
                                     e.getSalario());
            }
            else
            {
                salariosPorDpto.put (e.getDepartamento(), e.getSalario());
            }
        }
        
        // Para cada uno de los departamentos, mostrar su estadística.
        
        System.out.println ("Departamento\t| Nº empleados\t| Salario medio");
        // Recorro todas las entradas del mapa.
        for (Map.Entry<String,Integer> d : numEmpleadosPorDpto.entrySet())
        {
            //"%12s" es para que el String ocupe un tamaño fijo (En este caso 12)
            System.out.println (String.format ("%12s", d.getKey()) + "\t| " + 
                                String.format ("%12s", d.getValue().toString()) + "\t| " + 
                    String.format ("%.2f", salariosPorDpto.get(d.getKey()) / d.getValue()));
        }
    }


    ////////////////////////////////////////////////////////////////////////////
    // 3. Biblioteca. Consultas y filtrado
    //
    // A partir del dataset biblioteca.xml mostrar: 
    // a) El título de todos los libros
    // b) Contar cuántos libros hay de cada género  
    ////////////////////////////////////////////////////////////////////////////

    public static void Ejercicio3 ()
    {
        FileInputStream fis;
        
        // Mapa con la cantidad de libros por género.
        Map <String, Integer> numLibrosPorGenero = new HashMap <>();

        try 
        { 
            fis = new FileInputStream("C:\\2DAM\\AccesoaDatos\\Datasets-20261023\\biblioteca.xml");
        
            // Obtenemos el DocumentBuilderFactory necesario para parsear mediante DOM.
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        
            // Obtenemos el DocumentBuilder necesario para parsear mediante DOM.
            DocumentBuilder builder = factory.newDocumentBuilder();
        
            // Obtenemos el Document parseando mediante DOM.
            Document dom = builder.parse(fis);
        
            // Eliminamos nodos de texto redundantes (como saltos de línea o espacios).
            dom.getDocumentElement().normalize();
            
            // Obtenemos la lista de nodos con el tag "libro".
            NodeList items = dom.getElementsByTagName("libro");     

            // Mostrar títulos.
            String genero; 
            System.out.println ("TÍTULOS\n");
            
            // Recorremos cada uno de los nodos.
            for (int i = 0; i < items.getLength(); i++) 
            {
                // Obtenemos el nodo (libro) de la posición i.
                Element e = (Element) (items.item(i));                
                                
                // Valor del elemento "titulo".
                System.out.println (e.getElementsByTagName("titulo").item(0).getTextContent());

                // Obtención de los géneros del libro e inserción en el mapa.
                NodeList generos = e.getElementsByTagName ("genero");
                for (int k = 0; k < generos.getLength(); k++)
                {
                    genero= generos.item(k).getTextContent();
                    if (numLibrosPorGenero.containsKey(genero))
                    {
                        numLibrosPorGenero.put (genero,
                                     numLibrosPorGenero.get (genero) + 1);
                    }
                    else
                    {
                        numLibrosPorGenero.put (genero, 1);
                    }
                }
            }                         
        }
        catch (IOException ex)
        {
            System.out.println (ex.getMessage());
        }
        catch (ParserConfigurationException ex)
        {
            System.out.println (ex.getMessage());
        }
        catch (SAXException ex)
        {
            System.out.println (ex.getMessage());
        }

        
        // Mostrar el número de libros por género.
        System.out.println ("\n\nNº DE LIBROS POR GÉNERO\n");
        for (Map.Entry<String,Integer> d : numLibrosPorGenero.entrySet())
        {
            System.out.println (String.format ("%12s", d.getKey()) + ": " + d.getValue());
        }
    }
   

    ////////////////////////////////////////////////////////////////////////////
    // 4. Pedidos. Consulta, validación y totales
    //
    // Utilizando el dataset pedidos.xml: 
    // a) Mostrar por pantalla los items del pedido indicado por el usuario.
    // b) Recalcular el total del pedido como Σ(cantidad × precioUnitario) y 
    // comprobar si coincide con el valor que figura en el elemento <total>
    ////////////////////////////////////////////////////////////////////////////

    public static void Ejercicio4 ()
    {
        // Solicitud del código del pedido.
        String cod_introducido, codigo;
        Scanner sc = new Scanner (System.in);
        System.out.print ("Introduce el código del pedido: ");
        cod_introducido = sc.nextLine();
        sc.close ();
        
        FileInputStream fis;

        try 
        { 
            fis = new FileInputStream("C:\\2DAM\\AccesoaDatos\\Datasets-20261023\\pedidos.xml");
        
            // Obtenemos el DocumentBuilderFactory necesario para parsear mediante DOM.
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        
            // Obtenemos el DocumentBuilder necesario para parsear mediante DOM.
            DocumentBuilder builder = factory.newDocumentBuilder();
        
            // Obtenemos el Document parseando mediante DOM.
            Document dom = builder.parse(fis);
        
            // Eliminamos nodos de texto redundantes (como saltos de línea o espacios).
            dom.getDocumentElement().normalize();
            
            // Obtenemos la lista de nodos con el tag "pedido".
            NodeList items = dom.getElementsByTagName("pedido");     

            // Recorremos cada uno de los nodos.
            boolean encontrado = false;
            int cantidad;
            double precioUnitario, total, totalCalculado;
            
            for (int i = 0; i < items.getLength(); i++) 
            {
                // Obtenemos el nodo (pedido) de la posición i.
                Element pedido = (Element) (items.item(i));                
                                
                // Valor del atributo "id".
                codigo = pedido.getAttribute("id");
                
                if (cod_introducido.equals (codigo))
                {
                    encontrado = true;
                    
                    // Items del pedido.
                    
                    System.out.println ("ITEMS DEL PEDIDO\n");
                    
                    NodeList itemsPedido = pedido.getElementsByTagName("item");
                    totalCalculado = 0;
                    //Recorro todos los items del pedido para mostrarlos y asi calcular el total.
                    for (int k = 0; k < itemsPedido.getLength(); k++)
                    {
                        Element it = (Element) (itemsPedido.item(k));
                        
                        // Descripción.
                        System.out.println ("SKU: " + it.getAttribute("sku"));
                        System.out.println ("Descripcion: " + it.getElementsByTagName("descripcion").item(0).getTextContent());
                        
                        // Cantidad.
                        cantidad = Integer.parseInt (it.getElementsByTagName("cantidad").item(0).getTextContent());
                        System.out.println ("Cantidad: " + cantidad);
                        
                        // Precio unitario.
                        precioUnitario = Double.parseDouble (it.getElementsByTagName("precioUnitario").item(0).getTextContent());
                        System.out.println ("Precio unitario: " + String.format ("%.2f", precioUnitario) + "\n"  );
                        
                        totalCalculado += cantidad * precioUnitario;
                    }
                    
                    // Total.
                    total = Double.parseDouble (pedido.getElementsByTagName("total").item(0).getTextContent());
                    System.out.println ("TOTAL: " + String.format ("%.2f", total) + "\n"  );


                    // Comparar para saber si 2 numeros reales son practicamente iguales.
                    if (abs (totalCalculado - total) < 0.001)
                    {
                        System.out.println ("El total del pedido es CORRECTO.");
                    }
                    else
                    {
                        System.out.println ("El total del pedido es INCORRECTO.");
                    }
                    
                    break;
                }                    
            }
            
            // Mostrar mensaje de error si el pedido no ha sido encontrado.
            if (!encontrado) 
            {
                System.out.println ("Código no encontrado.");
            }
        }
        catch (IOException ex)
        {
            System.out.println (ex.getMessage());
        }
        catch (ParserConfigurationException ex)
        {
            System.out.println (ex.getMessage());
        }
        catch (SAXException ex)
        {
            System.out.println (ex.getMessage());
        }

    }
    

    ////////////////////////////////////////////////////////////////////////////
    // 5. Listado de alumnos
    // Cargar el dataset alumnos.json y mostrar el nombre y la fecha de 
    // nacimiento de los alumnos.
    ////////////////////////////////////////////////////////////////////////////

    public static JSONTokener cargarArchivoJSON (String path)
    {
        // Carga del archivo JSON.
        StringBuilder sb = new StringBuilder();
        try
        { 
             FileReader fr = new FileReader (path);
             BufferedReader br = new BufferedReader(fr); 
             String line;
             
             while ((line = br.readLine()) != null) 
             {
                 sb.append(line);
             }
        }
        catch (IOException ex)
        {
            System.out.println (ex.getMessage ());
        }
     
        JSONTokener tokener = new JSONTokener(sb.toString());
        return tokener;
    }


    public static void Ejercicio5 ()
    {
        // Carga del archivo JSON.
        JSONTokener tokener = cargarArchivoJSON ("C:\\2DAM\\AccesoaDatos\\Datasets-20261023\\alumnos.json");
             
        JSONArray alumnosJSON = new JSONArray(tokener);

        // Mostrar el nombre y fecha de nacimiento de los alumnos.
        System.out.println ("ALUMNOS\n");
        
        for (int i = 0; i < alumnosJSON.length(); i++) 
        {
            JSONObject alumnoJSON = alumnosJSON.getJSONObject(i);
            System.out.println ("Nombre: " + alumnoJSON.getString("nombre"));
            System.out.println ("Fecha de nacimiento: " + alumnoJSON.getString("fechaNacimiento") + "\n");
        }
    }
        
    
    ////////////////////////////////////////////////////////////////////////////
    // 6. Objetos y estadísticas
    // Mapear los datos de los alumnos a objetos Alumno, 
    // para ello también deberás mapear los notas a un array de objetos Nota. 
    // Después muestra la siguiente información:
    // a) Alumno y la asignatura con la nota más alta.
    // b) Alumno con la nota media más alta.
    ////////////////////////////////////////////////////////////////////////////

    public static void Ejercicio6 ()
    {
        // Carga del archivo JSON.
        JSONTokener tokener = cargarArchivoJSON ("C:\\2DAM\\AccesoaDatos\\Datasets-20261023\\alumnos.json");

        ArrayList<Alumno> alumnos = new ArrayList<>();        
        JSONArray alumnosJSON = new JSONArray(tokener);

        ArrayList<Nota> notas = null;
        
        for (int i = 0; i < alumnosJSON.length(); i++) 
        {
            JSONObject alumnoJSON = alumnosJSON.getJSONObject(i);
            
            // Notas del alumno.
            notas = new ArrayList <>();
            
            JSONArray notasJSON = alumnoJSON.getJSONArray("notas");
            for (int n = 0; n < notasJSON.length (); n++)
            {
                JSONObject notaJSON = notasJSON.getJSONObject (n);
                Nota nota = new Nota (notaJSON.getString ("asignatura").toString(),
                                      notaJSON.getDouble ("nota"));
                notas.add (nota);
            }
            Alumno alumno = new Alumno (alumnoJSON.getString("id"),
                                        alumnoJSON.getString("nombre"),
                                        alumnoJSON.getBoolean ("matriculado"),
                                        alumnoJSON.getString("fechaNacimiento"),
                                        notas
                                       );
            alumnos.add (alumno);
        }
        // Mostrar las estadísticas de los alumnos.
        mostrarDatosAlumnos (alumnos);       
    }
    
    
    // Mostrar los datos de los alumnos.
    static void mostrarDatosAlumnos (ArrayList <Alumno> alumnos)
    {
        // Media del primer alumno.
        String nombreMediaMayor = alumnos.get(0).getNombre();
        double mediaMayor = alumnos.get(0).notaMedia();
        
        // Alumno con mejor nota.
        String nombreMejorNota = alumnos.get(0).getNombre();
        Nota mejorNota = alumnos.get(0).notaMaxima();

        // Comparamos los datos del primer alumno con los del resto.
        double media;
        Nota nota;
        for (int a= 1; a < alumnos.size(); a++)
        {   
            // Para el dato de la mejor media.
            media = alumnos.get(a).notaMedia();
            if (media > mediaMayor)
            {
                mediaMayor = media;
                nombreMediaMayor = alumnos.get(a).getNombre();
            }
            
            // Para el dato de la mejor nota.
            nota = alumnos.get(a).notaMaxima();
            if (nota.getNota() > mejorNota.getNota())
            {
                mejorNota = nota;
                nombreMejorNota = alumnos.get(a).getNombre ();
            }
        }
        
        System.out.println ("El alumno con mejor media es: " + 
                            nombreMediaMayor + ": " + String.format("%.1f", mediaMayor)); 
        
        System.out.println ("La mejor nota la obtuvo: " + nombreMejorNota + " en " + 
                            mejorNota.getAsignatura() + ": " + String.format("%.1f",mejorNota.getNota()));
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    // 7. Consulta de inventario
    // Cargar el dataset inventario.json y mostrar la ubicación del producto 
    // indicado por el usuario.
    ////////////////////////////////////////////////////////////////////////////

    public static void Ejercicio7 ()
    {
        // Solicitud del código del producto.
        String cod_introducido;
        Scanner sc = new Scanner (System.in);
        System.out.print ("Introduce el código del producto: ");
        cod_introducido = sc.nextLine();
        sc.close ();
        
        // Carga del archivo JSON.
        JSONTokener tokener = cargarArchivoJSON ("C:\\2DAM\\AccesoaDatos\\Datasets-20261023\\inventario.json");
       
        JSONObject inventarioJSON = new JSONObject(tokener);
        JSONArray productosJSON = inventarioJSON.getJSONArray("productos");
        
        
        boolean encontrado = false;
        for (int i = 0; i < productosJSON.length(); i++) 
        {
            JSONObject productoJSON = productosJSON.getJSONObject(i);
            
            if (productoJSON.getString ("id").equals (cod_introducido))
            {
                encontrado = true;
                JSONObject ubicacionJSON = productoJSON.getJSONObject("ubicacion");
                System.out.println ("El producto " + cod_introducido + 
                                    " se encuentra en el pasillo " + 
                                    ubicacionJSON.getInt ("pasillo") + 
                                    ", estante " + 
                                    ubicacionJSON.getString ("estante")
                                    );
                break;
            }
        }

        if (encontrado == false)
        {
            System.out.println ("Producto no encontrado.");
        }
    } 
        
        
    ////////////////////////////////////////////////////////////////////////////
    // 8. Peliculas
    // a) Calcular la puntuación media de cada película. Para ello hay que 
    // normalizar primero la puntuación rt que está en escala 1-100 y pasarla 
    // a escala 1-10 que es la que utiliza imbd.
    // Después mostrar todas las películas ordenadas por media en orden 
    // descendente.
    // b) Mostrar todos los géneros distintos de películas ordenados 
    // alfabéticamente.
    ////////////////////////////////////////////////////////////////////////////

    public static void Ejercicio8 ()
    {
        // Carga del archivo JSON.
        JSONTokener tokener = cargarArchivoJSON ("C:\\2DAM\\AccesoaDatos\\Datasets-20261023\\peliculas.json");
       
        JSONObject pJSON = new JSONObject(tokener);
        JSONArray peliculasJSON = pJSON.getJSONArray("peliculas");
        
        // Lista de películas.
        ArrayList <Pelicula> peliculas = new ArrayList <>();
        ArrayList <String> generos;        

        // Conjunto ordenado de géneros.
        // Un treeset almacena elementos sin repetir ordenados alfabéticamente de manera por defecto.
        Set <String>generosSet = new TreeSet <> ();

        for (int i = 0; i < peliculasJSON.length(); i++) 
        {
            JSONObject peliculaJSON = peliculasJSON.getJSONObject(i);
            
            // Recorrido de los géneros.
            JSONArray generosJSON = peliculaJSON.getJSONArray("generos");
            generos = new ArrayList<>();
            for (int g = 0; g < generosJSON.length(); g++)
            {
                generos.add (generosJSON.get(g).toString());
                generosSet.add (generosJSON.get(g).toString());                
            }
            
            Pelicula p = new Pelicula (peliculaJSON.getString("id"),
                                       peliculaJSON.getString ("titulo"),
                                       peliculaJSON.getString ("director"),
                                       peliculaJSON.getInt ("estreno"),
                                       peliculaJSON.getInt ("duracionMin"),
                                       generos,
                                       peliculaJSON.getJSONObject("puntuaciones").getDouble("imdb"),
                                       peliculaJSON.getJSONObject("puntuaciones").getDouble("rt")
                                      );
            peliculas.add (p);
        }
        
        // Ordenación de las películas por puntuación media.
        System.out.println ("Películas en orden descendente de puntuación media:\n");
        // El collections es una clase de java que permite ordenar listas y el sort hara la ordenación
        // según el criterio del compareTo de Pelicula.
        Collections.sort(peliculas);
        for (Pelicula p : peliculas)
        {
            System.out.println ("Id: " + p.getId() + " - Título: " + p.getTitulo() +
            "\nPuntuación media: (imdb: " + String.format("%.1f", p.getPuntuacionImdb()) + 
            " , rt: " + String.format("%.1f", p.getPuntuacionRt()) + "): " + 
            p.puntuacionMedia() + "\n");
        }
        
        // Mostrar los diferentes géneros en orden alfabético.
        System.out.println ("\nGéneros en orden alfabético:");
        System.out.println (generosSet);
        
    } 
    
}
