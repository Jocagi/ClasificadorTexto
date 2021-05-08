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
                if (linea.contains("|")) {
                  String[] parts = linea.split("\\|");
                    if (parts.length == 2) {
                            parts[1] = parts[1].trim();
                        if (parts[0]!= "") {
                            String lastCharacter = linea.substring(linea.length() - 1);
                            char[] lastChar = lastCharacter.toCharArray();
                            if(lastChar[0] != '|')
                            {
                                lista.add(linea);
                            } 
                        }
                    }                    
                }
            }
            lector.close();
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,e);
        }
        return lista;
    }
}
