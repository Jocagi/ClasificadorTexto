/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyecto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author genec
 */
public class Almacen {
    static BufferedReader lector;
        static String linea;
        public static ArrayList<String> lista = new ArrayList();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Conteo();
    }
    public static void Conteo(){
        //Aquí se manda a leer el archivo
    leer("C:\\Users\\genec\\OneDrive\\Escritorio\\test.csv");
    //Creacion diccionario de etiquetas
        Map<String, Map<String, Integer>>  Etiquetas = new HashMap<String, Map<String, Integer>>();
        //Vectores para separación
        String[] Frases;
        String[] Palabras;
        //For each para la lista que contiene linea por linea del csv
        for(String str : lista)
        {
            Frases = str.split("\\|");
            String Tag = Frases[1];
            String Frase = Frases[0];
            //Eliminación caracteres especiales
            if(Etiquetas.containsKey(Tag)){
                Frase = Frase.replace('.', ' ');
                Frase = Frase.replace('?', ' ');
                Frase = Frase.replace('¿', ' ');
                Frase = Frase.replace('!', ' ');
                Frase = Frase.replace('¡', ' ');
                Frase = Frase.replace('(', ' ');
                Frase = Frase.replace(')', ' ');
                Frase = Frase.replace(',', ' ');
                Frase = Frase.replace('"', ' ');
                Palabras = Frase.split(" ");
                //Aquí cuento la cantidad de palabras por etiqueta
                //Y las almaceno según el idioma si ya existe la etiqueta
                for (int i = 0; i < Palabras.length; i++) {
                    Integer oldCount = Etiquetas.get(Tag).get(Palabras[i]);
                    if(oldCount == null)
                    {
                        oldCount = 0;
                    }
                    Etiquetas.get(Tag).put(Palabras[i], oldCount +1);
                    //Elimino espacios vacíos
                    Etiquetas.get(Tag).remove("");
                }
            }
            //Creo el diccionario si en caso la etiqueta "Llave" aún no existe
            else{
                Etiquetas.put(Tag, new HashMap<String, Integer>());
                Frase = Frase.replace('.', ' ');
                Frase = Frase.replace('?', ' ');
                Frase = Frase.replace('¿', ' ');
                Frase = Frase.replace('!', ' ');
                Frase = Frase.replace('¡', ' ');
                Frase = Frase.replace('(', ' ');
                Frase = Frase.replace(')', ' ');
                Frase = Frase.replace(',', ' ');
                Frase = Frase.replace('"', ' ');
                Palabras = Frase.split(" ");
                for (int i = 0; i < Palabras.length; i++) {
                    Integer oldCount = Etiquetas.get(Tag).get(Palabras[i]);
                    if(oldCount == null)
                    {
                        oldCount = 0;
                    }
                    Etiquetas.get(Tag).put(Palabras[i], oldCount +1);
                    //Elimino espacios vacíos
                    Etiquetas.get(Tag).remove("");
                }
            }
        }
        System.out.println(Etiquetas);
    }
    //Metodo de Kevin de lectura de archivo
    public static void leer(String nombreArchivo){
        try{
            lector = new BufferedReader(new FileReader(nombreArchivo));
            while((linea=lector.readLine()) != null){
                lista.add(linea);
            }
            lector.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
}
