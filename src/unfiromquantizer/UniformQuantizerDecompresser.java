package unfiromquantizer;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;
import javax.swing.JOptionPane;
//import javafx.util.Pair;

/**
 *
 * @author ahmed
 */
public class UniformQuantizerDecompresser {
    //public void decompressImg()
    private static int width , height;
    private static int n;
    private static int [][]pixles = null;
    private static int readCompressedPixles (File pixlesFile ){
        
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(pixlesFile);
            ois = new ObjectInputStream(fis); 
            n= ois.readShort();
            height = ois.readInt();
            width = ois.readInt();
            pixles = new int[height][width];
            for(int i=0 ; i < height ; i++ )
                for(int j=0 ; j<width ; j++ )
                    pixles[i][j] = ois.readShort();
        }
        catch(FileNotFoundException e ){
            JOptionPane.showMessageDialog(null, e, "File not found", n);
        }
        catch( IOException e){
            JOptionPane.showMessageDialog(null, e, "I/O Error", n);
        }
         
        return n; 
    }
    
    public static void decompressImg(File pixlesFile , File imageFile  ){
        pixles = null ;
        n = readCompressedPixles( pixlesFile );
        int quantizer = (int) Math.pow(2, n);
        int step = 256/quantizer; 
        System.out.println(pixles.length + "  "+ pixles[0].length);
        for(int i=0 ; i < pixles.length ; i++ )
            for(int j=0 ; j<pixles[i].length ; j++)
                pixles[i][j] =( pixles[i][j] * step )  + ( step / 2 );
        
        ImageRW.height = height;
        ImageRW.width = width;
        ImageRW.writeImage(pixles, imageFile);
        JOptionPane.showMessageDialog(null, "Decompressed Successfully");
    }
}
