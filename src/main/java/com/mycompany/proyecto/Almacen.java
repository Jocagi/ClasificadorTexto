/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyecto;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author genec
 */
public class Almacen {
        //variables de lectura de archivo
        static BufferedReader lector;
        static String linea;
        public static ArrayList lista = new ArrayList<String>();

    public static void main(String[] args) {
        
        //leer archivo
        leer("rutaArchivo");

    }

    //método para laer archivo
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
