/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unfiromquantizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import javax.swing.JOptionPane;

/**
 *
 * @author ahmed
 */
public class UnfiromQuantizerCompresser {

    /**
     * @param args the command line arguments
     */ 
    private static int numOfPixles;
    private static int MSE;
    static void compressImg(File ImageFile ,int n ,File pixlesFile){
       MSE = 0;
       int[][]pixles = ImageRW.readImage(ImageFile);
       int quantizer = (int) Math.pow(2, n);
       int step = 256/quantizer;
       for(int i = 0 ; i < pixles.length ; i++ )
           for(int j=0 ; j <pixles[i].length ; j++ ) {  
              
               int originalValue =  pixles[i][j] ;
               pixles[i][j] /= step;   //convert value to its level
               int newValue = pixles[i][j] * step  + step/2 ;
               MSE+= Math.pow(newValue - originalValue, 2);
               
               if( i<50 & j<50 ){
                   System.out.println("originalValue = "+ originalValue);
                   System.out.println("newValue = "+ newValue);
                   System.out.println("MSE= "+  MSE);
               
               }
               
           }
       
        writePixles( pixlesFile, pixles, n);
        numOfPixles = pixles.length * pixles[0].length ;
        MSE /= numOfPixles;
        JOptionPane.showMessageDialog(null,"Compress Successfully\nMSE = "+MSE);
        
    }
    static void writePixles(File pixlesFile,  int[][]pixles , int n ){
        
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(pixlesFile);
            oos = new ObjectOutputStream(fos);

            oos.writeShort(n);
            oos.writeInt(pixles.length);
            oos.writeInt(pixles[0].length);
            for (int i = 0; i < pixles.length; i++) {
                for (int j = 0; j < pixles[i].length; j++) {
                    oos.writeShort(pixles[i][j]);
                   
                } 
            }
            oos.close();
            fos.close();

        }
        catch(FileNotFoundException e ){
            JOptionPane.showMessageDialog(null, e, "File not found", n); 
        }
        catch( IOException e){
            JOptionPane.showMessageDialog(null, e, "I/O Error", n); 
        }
    }
    
     
    
}
