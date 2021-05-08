package com.mycompany.proyecto;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bayes {

    private Map<String, Map<String, Integer>>  Etiquetas = new HashMap<String, Map<String, Integer>>();
    private Map<String, Integer>  TotalL = new HashMap<String,Integer>();
    private Map<String, Integer>  PalabrasEnEtiqueta = new HashMap<String,Integer>();
    private Map<String, Map<String, Double>> ProbabilityWord = new HashMap<String, Map<String, Double>>();
    private Map<String, Double> ProbabilityTag = new HashMap<String, Double>();
    private ArrayList<String> Entrenamiento;
    private int totalMensajes = 0;
    private int totalPalabras = 0;
    private int totalEtiquetas = 0;

    public Bayes(ArrayList<String> Entrenamiento){
        this.Entrenamiento = Entrenamiento;
        Conteo();
        CalcularProbabilidadPorPalabra();
        CalcularProbabilidadPorEtiqueta();
        JOptionPane.showMessageDialog(null, "Archivo leído con éxito");
    }

    private String fraseEstandarizada(String input)
    {
        input = input.replaceAll("\\p{Punct}", " ");
        input = input.replaceAll("\\pP|\\pS|\\pC|\\pN|\\pZ", " ");
        input = input.toUpperCase();
        return input;
    }

    private void Conteo(){
        //Aquí se manda a leer el archivo
        //Creacion diccionario de etiquetas
        //Vectores para separación
        String[] Frases;
        String[] Palabras;
        int cantidadT = 1;
        //For each para la lista que contiene linea por linea del csv
        for(String str : Entrenamiento)
        {
            totalMensajes++;
            Frases = str.split("\\|");
            String Tag = Frases[Frases.length - 1];
            String Frase = Frases[0];

            //Eliminación caracteres especiales
            Frase = fraseEstandarizada(Frase);
            //Eliminación de espacios vacíos
            Frase = Frase.replaceAll("^ +| +$|( )+", "$1");
            //Obtener palabras en frase
            Palabras = Frase.split(" ");

            if(this.Etiquetas.containsKey(Tag)){
                TotalL.put(Tag, TotalL.get(Tag) + 1);
            }
            //Creo el diccionario si en caso la etiqueta "Llave" aún no existe
            else{
                Etiquetas.put(Tag, new HashMap<String, Integer>());
                PalabrasEnEtiqueta.put(Tag, 0);
                TotalL.put(Tag,cantidadT++);
                cantidadT = 1;
                totalEtiquetas++;
            }

            //Sumar total de palabras en etiqueta al diccionario
            PalabrasEnEtiqueta.put(Tag, PalabrasEnEtiqueta.get(Tag) + Palabras.length);

            //Aquí cuento la cantidad de palabras por etiqueta
            //Y las almaceno según el idioma si ya existe la etiqueta
            for (int i = 0; i < Palabras.length; i++) {
                totalPalabras++;
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

    private void CalcularProbabilidadPorPalabra(){
        //Recorre el diccionario por etiquetas
        for (Map.Entry<String, Map<String, Integer>> KeyL : Etiquetas.entrySet()){
            Map<String, Double> ProbabilityW = new HashMap<String, Double>();
            String etiqueta = KeyL.getKey();
            //Se recorre otra vez el diccionaro para poder saber la probabilidad de cada palabra en cada etiqueta
            for(var result : KeyL.getValue().entrySet()){
                //se guarda en un diccionario conforme a su etiqueta, palabra y probabilidad
                double operation = (double) result.getValue() / (double) PalabrasEnEtiqueta.get(etiqueta);
                ProbabilityW.put(result.getKey(), operation);
                ProbabilityWord.put(etiqueta, ProbabilityW);
            }
        }
    }

    private void CalcularProbabilidadPorEtiqueta(){
        //Calculo individual de probabilidad por etiqueta
        for (var value : TotalL.entrySet()){
            double operacion = (double) value.getValue() / (double) totalMensajes;
            ProbabilityTag.put(value.getKey(), operacion);
        }
    }

    public Predicción Inferir(String texto){
        texto = fraseEstandarizada(texto);
        String[] palabras = texto.split(" ");
        Map<String, Double> ProbabilityTagBayes = new HashMap<String, Double>();

        double mayor = Double.NEGATIVE_INFINITY;
        String etiquetaMayor = Etiquetas.keySet().toArray()[0].toString();
        String valores = "";

        //Hacer calculo de probabilidad
        for (var etiqueta: Etiquetas.entrySet()) {

            double puntaje = 0;
            String nombreEtiqueta = etiqueta.getKey();

            //Efectuar operacion P(E) * P(W1|E) * P(W2|E) ... * P(Wn|E)
            for (var palabra: palabras) {
                if (ProbabilityWord.containsKey(nombreEtiqueta) && ProbabilityWord.get(nombreEtiqueta).containsKey(palabra))
                {
                    //Aplica logaritmo natural para prevenir un underflow
                    puntaje += Math.log(ProbabilityWord.get(nombreEtiqueta).get(palabra));
                }
                else {
                    puntaje += Math.log(0.000000000000000001);
                }
            }
            puntaje += Math.log(ProbabilityTag.get(etiqueta.getKey()));
            ProbabilityTagBayes.put(etiqueta.getKey(), puntaje);

            //Elegir el mayor
            if (puntaje > mayor)
            {
                mayor = puntaje;
                etiquetaMayor = etiqueta.getKey();
            }

            //Documentar otros valores
            valores += "\n" + nombreEtiqueta + " -> " + puntaje;
        }

        return new Predicción(etiquetaMayor, valores);
    }
}
