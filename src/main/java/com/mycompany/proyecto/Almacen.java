/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyecto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        Map<String, Integer> Ocurrencias = new HashMap<String, Integer>();
        List<String> ejemploLista = new ArrayList();
        List<String> ejemploLista2 = new ArrayList();
        String[] Frases;
        String[] Palabras;
        String Probando = "Hola como estas|Español";
        String Prueba = "Bye my friends estas|Ingles";
        ejemploLista.add(Prueba);
        ejemploLista.add(Probando);
        for(String str : ejemploLista)
        {
            Frases = str.split("\\|");
            String Tag = Frases[1];
            String Frase = Frases[0];
            ejemploLista2.add(Frase);
            Etiquetas.put(Tag, Frase);
        }
        for ( String Palabra : ejemploLista2 )
            {
                Palabras = Palabra.split(" ");
                System.out.println(Palabras.toString());
                for (int i = 0; i < Palabras.length; i++) {
                    Integer oldCount = Ocurrencias.get(Palabras[i]);
                    if(oldCount == null)
                    {
                        oldCount = 0;
                    }
                    Ocurrencias.put(Palabras[i], oldCount + 1);
                }
            }
                System.out.println(Ocurrencias);
    }
    
}
