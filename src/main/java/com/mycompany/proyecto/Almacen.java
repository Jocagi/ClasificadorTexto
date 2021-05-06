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
        Map<String, Map<String, Integer>>  Etiquetas = new HashMap<String, Map<String, Integer>>();
//        Map<String, Integer> Ocurrencias = new HashMap<String, Integer>();
        List<String> ejemploLista = new ArrayList();
//        List<String> ejemploLista2 = new ArrayList();
        String[] Frases;
        String[] Palabras;
        String Probando = "Hola como estas|Español";
        String Prueba = "Bye my tio|Ingles";
        String Prueba2 = "Bye my tio|Ingles";
        ejemploLista.add(Prueba);
        ejemploLista.add(Prueba2);
        ejemploLista.add(Probando);
        for(String str : ejemploLista)
        {
            Frases = str.split("\\|");
            String Tag = Frases[1];
            String Frase = Frases[0];
            
            if(Etiquetas.containsKey(Tag)){
                
                Palabras = Frase.split(" ");
                for (int i = 0; i < Palabras.length; i++) {
                    Integer oldCount = Etiquetas.get(Tag).get(Palabras[i]);
                    if(oldCount == null)
                    {
                        oldCount = 0;
                    }
                    Etiquetas.get(Tag).put(Palabras[i], oldCount +1);
                }
            }
            else{
                Etiquetas.put(Tag, new HashMap<String, Integer>());
                Palabras = Frase.split(" ");
                for (int i = 0; i < Palabras.length; i++) {
                    Integer oldCount = Etiquetas.get(Tag).get(Palabras[i]);
                    if(oldCount == null)
                    {
                        oldCount = 0;
                    }
                    Etiquetas.get(Tag).put(Palabras[i], oldCount +1);
                }
            }
        }
                System.out.println(Etiquetas);
    }
    
}
