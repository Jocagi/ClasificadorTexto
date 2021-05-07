package com.mycompany.proyecto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bayes {

    private Map<String, Map<String, Integer>>  Etiquetas = new HashMap<String, Map<String, Integer>>();
    private Map<String, Integer>  TotalL = new HashMap<String,Integer>();
    private Map<String, Map<String, Double>> ProbabilityWord = new HashMap<String, Map<String, Double>>();
    private Map<String, Double> ProbabilityTag = new HashMap<String, Double>();
    private ArrayList<String> Entrenamiento;
    private int totalPalabras = 0;

    public Bayes(ArrayList<String> Entrenamiento){
        this.Entrenamiento = Entrenamiento;
        Conteo();
        CalcularProbabilidadPorPalabra();
        CalcularProbabilidadPorEtiqueta();
    }

    private String fraseNormalizada(String input)
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
            Frases = str.split("\\|");
            String Tag = Frases[1];
            String Frase = Frases[0];

            //Eliminación caracteres especiales
            Frase = fraseNormalizada(Frase);
            Palabras = Frase.split(" ");

            if(this.Etiquetas.containsKey(Tag)){
                TotalL.put(Tag, TotalL.get(Tag) + 1);
            }
            //Creo el diccionario si en caso la etiqueta "Llave" aún no existe
            else{
                Etiquetas.put(Tag, new HashMap<String, Integer>());
                TotalL.put(Tag,cantidadT++);
                cantidadT = 1;
            }

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
    }

    private void CalcularProbabilidadPorPalabra(){
        //Recorre el diccionario por etiquetas
        for (Map.Entry<String, Map<String, Integer>> KeyL : Etiquetas.entrySet()){
            int counterT = 0;
            Map<String, Double> ProbabilityW = new HashMap<String, Double>();
            //Recorre el diccionario de la etiqueta para saber la cantidad de palabras
            for(Map.Entry<String, Integer> ValW : KeyL.getValue().entrySet() ){
                counterT += ValW.getValue();
            }
            //Se recorre otra vez el diccionaro para poder saber la probabilidad de cada palabra en cada etiqueta
            for(Map.Entry<String, Integer> result : KeyL.getValue().entrySet()){
                //se guarda en un diccionario conforme a su etiqueta, palabra y probabilidad
                double operation = (double) result.getValue() / (double) counterT;
                ProbabilityW.put(result.getKey(), operation);
                ProbabilityWord.put(KeyL.getKey(), ProbabilityW);
            }
            totalPalabras += counterT;
        }
    }

    private void CalcularProbabilidadPorEtiqueta(){
        int mensajes = 0;
        //Recorre el diccionario de mensajes por etiqueta para contar total de mensajes
        for (var value : TotalL.entrySet()){
            mensajes += value.getValue();
        }
        //Calculo individual de proabilidad por etiqueta
        for (var value : TotalL.entrySet()){
            double operacion = (double) value.getValue() / (double) mensajes;
            ProbabilityTag.put(value.getKey(), operacion);
        }
    }

    public String Inferir(String texto){
        texto = fraseNormalizada(texto);
        String[] palabras = texto.split(" ");
        double result = 0;
        Map<String, Double> ProbabilityTagBayes = new HashMap<String, Double>();
        double mayor = Double.NEGATIVE_INFINITY;
        String etiquetaMayor = "";

        //Hacer calculo de probabilidad
        for (var etiqueta: Etiquetas.entrySet()) {

            double puntaje = 0;

            String nombreEtiqueta = etiqueta.getKey();

            for (var palabra: palabras) {
                if (ProbabilityWord.containsKey(nombreEtiqueta) && ProbabilityWord.get(nombreEtiqueta).containsKey(palabra))
                {
                    puntaje += Math.log(ProbabilityWord.get(nombreEtiqueta).get(palabra));
                }
                else {
                    puntaje += Math.log(0.00000000000001);
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
        }

        return etiquetaMayor;
    }
}
