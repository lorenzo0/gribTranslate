/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LetturaGrib;

import java.io.*;
import java.util.*;

/**
 *
 * @author loren
 */
public class LetturaGrib {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        //File fileGrib = new File("format.grib");   
        int cont=0;
        
          try{    
            FileInputStream fin=new FileInputStream("format.grib");    
            int i=0;    
            while((i=fin.read())!=-1){    
             System.out.print((char)i);    
             cont++;
            }    
            fin.close();    
          }catch(Exception e){System.out.println(e);}   
          
          /*for(int index = 0; index < cont; index+=9) {
            String temp = fin.read().substring(index, index+8);
            int num = Integer.parseInt(temp,2);
            char letter = (char) num;
            s = s+letter;
        }*/
                }    
}

