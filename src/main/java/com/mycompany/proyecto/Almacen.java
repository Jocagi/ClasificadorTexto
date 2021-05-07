/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyecto;

import javax.swing.*;
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

        Bayes modelo = new Bayes(Parser.obtenerDatos(path));
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
}
