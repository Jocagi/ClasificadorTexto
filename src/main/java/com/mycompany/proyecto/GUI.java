/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyecto;

import javax.swing.*;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.xml.transform.Result;
import java.util.Scanner;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author karen
 */
public class GUI extends Frame{
    
    private Button b1, b2, b3;
    public Label lblInput, lblWord, lblWord1, lblWord2;
    public TextField tfInput, tfWord;
    public TextField tfCount;
    public JFileChooser chooser;
    File file = null;
    public static String pathToFile;
    public Bayes modelo;
    public String mensajeExtra = "NO SE HA HECHO NINGUNA OPERACiÓN";

    public GUI()
    {
        super ("Clasificador de texto");
        setLayout(new FlowLayout());
        lblWord1 = new Label("                Idioma:");
        lblInput = new Label("Ingrese un archivo para analizar: ");
        add(lblInput);     
        tfInput = new TextField(20); 
        tfInput.setEditable(false);
        add(tfInput);
        b1 = new Button("   Subir   ");
        add(b1);
        BtnSubir listener = new BtnSubir();
        b1.addActionListener(listener);        
        lblWord = new Label("              Ingrese la frase a evaluar: ");
        add(lblWord); 
        tfWord = new TextField(20); 
        add(tfWord);
        b2 = new Button("   Evaluar  ");
        add(b2);
        BtnEvaluar listener2 = new BtnEvaluar();
        b2.addActionListener(listener2);        
        b2.setBackground(Color.ORANGE);
        b1.setBackground(Color.ORANGE);
        add(lblWord1);
        tfCount = new TextField(10); // construct the TextField component with initial text
        tfCount.setEditable(false);       // set to read-only
        add(tfCount);
        b3 = new Button("Ver más");
        b3.setBackground(Color.ORANGE);
        add(b3);        
        BtnVerMas listener3 = new BtnVerMas();
        b3.addActionListener(listener3);        
        setSize(500,150);
        addWindowListener(new WindowAdapter(){  
            public void windowClosing(WindowEvent e) {  
                dispose();  
            }  
        });         
        setVisible(true);
    }
    private class BtnSubir implements ActionListener {
      // ActionEvent handler - Called back upon button-click.
      @Override
      public void actionPerformed(ActionEvent evt) {
        
        String path = "";
        path = tfInput.getText();
        chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("csv","csv");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(GUI.this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
           tfInput.setText(chooser.getSelectedFile().getAbsolutePath());
           path = chooser.getSelectedFile().getAbsolutePath();
           GUI.pathToFile = path;
           modelo = new Bayes(Parser.obtenerDatos(path));
        }
      }
   }
    private class BtnEvaluar implements ActionListener {
      // ActionEvent handler - Called back upon button-click.
      @Override
      public void actionPerformed(ActionEvent evt) {
         String palabra = tfWord.getText();
          if (modelo ==  null) {
              JOptionPane.showMessageDialog(null, "No existen datos para el análisis");
          }
          else
          {
                Predicción IDIOMA = modelo.Inferir(palabra);//IDIOMA RESULTADO
                String resultado = IDIOMA.resultado;
                mensajeExtra = IDIOMA.otros;
                tfCount.setText(resultado);
          }
      }
   }
    private class BtnVerMas implements ActionListener 
   {
      // ActionEvent handler - Called back upon button-click.
      @Override
      public void actionPerformed(ActionEvent evt) {         
         JOptionPane.showMessageDialog(null, mensajeExtra);
      }
   }
}