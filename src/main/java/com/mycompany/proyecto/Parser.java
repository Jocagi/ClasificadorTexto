package com.mycompany.proyecto;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Parser {

    //Metodo de Kevin de lectura de archivo
    public static ArrayList<String> obtenerDatos(String nombreArchivo){
        BufferedReader lector;
        String linea;
        ArrayList<String> lista = new ArrayList();

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

        return lista;
    }
}
