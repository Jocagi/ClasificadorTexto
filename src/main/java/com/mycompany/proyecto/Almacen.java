/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyecto;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author genec
 */
public class Almacen {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Map<String, String>  Etiquetas = new HashMap<String, String>();
        Map<String, Integer> Palabras = new HashMap<String, Integer>();
        String[] Frases;
        String Probando = "Hola como estas | Español";
        String parte1;
        String parte2;
        Frases = Probando.split("\\|");
        String Tag = Frases[0];
        String frase = Frases[1];
        
    }
    
}
