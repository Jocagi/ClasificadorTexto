/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyecto;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.xml.transform.Result;
import java.util.Scanner;

/**
 *
 * @author genec
 */
public class Almacen extends Frame{
    
    private Button b1, b2, b3;
    public Label lblInput, lblWord, lblWord1, lblWord2;
    public TextField tfInput, tfWord;
    public TextField tfCount;
    
    public Almacen()
    {        
        super ("Clasificador de texto");
        setLayout(new FlowLayout());
        lblWord1 = new Label("                                                                                                                                        ");
        lblInput = new Label("Ingrese un archivo para analizar: ");
        add(lblInput);     
        tfInput = new TextField(20); 
        add(tfInput);
        b1 = new Button("Subir");
        add(b1);
        BtnSubir listener = new BtnSubir();
        b1.addActionListener(listener);
        add(lblWord1);
        lblWord = new Label("Ingrese la palabra a evaluar: ");
        add(lblWord); 
        tfWord = new TextField(20); 
        add(tfWord);
        b2 = new Button("Evaluar");
        add(b2);
        BtnEvaluar listener2 = new BtnEvaluar();
        b2.addActionListener(listener2);
        tfCount = new TextField(10); // construct the TextField component with initial text
        tfCount.setEditable(false);       // set to read-only
        add(tfCount);
        setSize(500,200);
        setVisible(true);
    }
    private class BtnSubir implements ActionListener {
      // ActionEvent handler - Called back upon button-click.
      @Override
      public void actionPerformed(ActionEvent evt) {
        String path = "";
        path = tfInput.getText();
        leer(path);
        Conteo();
      }
   }
   private class BtnEvaluar implements ActionListener {
      // ActionEvent handler - Called back upon button-click.
      @Override
      public void actionPerformed(ActionEvent evt) {
         String palabra = tfWord.getText();
         //AQUÍ PONER LLAMADA A TU MÉTODO
         String IDIOMA = "";//IDIOMA RESULTADO
         tfCount.setText(IDIOMA); // Convert int to String
      }
   }
    
    static BufferedReader lector;
    static String linea;
    public static ArrayList<String> lista = new ArrayList();

    public static void main(String[] args) {
        // TODO code application logic here
        Scanner input = new Scanner(System.in);
        //System.out.println("Ingrese un archivo para analizar: ");
        Almacen v1 = new Almacen();
        
    }
    public static void Conteo(){
        //Aquí se manda a leer el archivo
        //Creacion diccionario de etiquetas
        Map<String, Map<String, Integer>>  Etiquetas = new HashMap<String, Map<String, Integer>>();
        Map<String, Integer>  TotalL = new HashMap<String,Integer>();
        Map<String, Double> ProbabilityW = new HashMap<String, Double>();
        Map<String, Map<String, Double>> ProbabilityT = new HashMap<String, Map<String, Double>>();
        //Vectores para separación
        String[] Frases;
        String[] Palabras;
        int cantidadT = 1;
        //For each para la lista que contiene linea por linea del csv
        for(String str : lista)
        {
            Frases = str.split("\\|");
            String Tag = Frases[1];
            String Frase = Frases[0];
            //Eliminación caracteres especiales
            if(Etiquetas.containsKey(Tag)){
                TotalL.put(Tag, TotalL.get(Tag) + 1);
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
                TotalL.put(Tag,cantidadT++);
                cantidadT = 1;
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

        //Recorre el diccionario por etiquetas
        for (Map.Entry<String, Map<String, Integer>> KeyL : Etiquetas.entrySet()){
            int counterT = 0;
            //Recorre el diccionario de la etiqueta para saber la cantidad de palabras
            for(Map.Entry<String, Integer> ValW : KeyL.getValue().entrySet() ){
                counterT += ValW.getValue();
            }
            //Se recorre otra vez el diccionaro para poder saber la probabilidad de cada palabra en cada etiqueta
            for(Map.Entry<String, Integer> result : KeyL.getValue().entrySet()){
                ProbabilityW.put(result.getKey(),(double) result.getValue() / (double) counterT);
                //se guarda en un diccionario conforme a su etiqueta, palabra y probabilidad
                ProbabilityT.put(KeyL.getKey(), ProbabilityW);
            }
        }

    }
    //Metodo de Kevin de lectura de archivo
    public static void leer(String nombreArchivo){
        try{
            lector = new BufferedReader(new FileReader(nombreArchivo));
            while((linea=lector.readLine()) != null){
                lista.add(linea);
            }
            JOptionPane.showMessageDialog(null, "Leído con éxito");
            lector.close();
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
}
