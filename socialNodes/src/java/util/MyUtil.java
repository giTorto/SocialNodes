/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletException;

/**
 *
 * @author Giulian
 */
public class MyUtil {
     public static ArrayList<String> parseFromString(String phrase_inviti) {
        ArrayList<String> retval = new ArrayList<String>();
        retval.clear();
        String delims = "[,]";
        String[] tokens = phrase_inviti.split(delims);
        retval.addAll(Arrays.asList(tokens));
        return retval;

    }
     
     public static boolean isImage(File f){
         
         String mimetype= new MimetypesFileTypeMap().getContentType(f );
         
         String type = mimetype.split("/")[0];
         if(type.equals("image")){
             return true;
         }else{
             f.delete();
             return false;
         }

     }

    
         /**
     * Su alcuni browser (IE) il file viene caricato con il path assoluto,
     * quindi bisogna recuperare il solo nome del file
     *
     * @param fileName
     * @return vero nome del file
     */
    public static String formatName(String fileName) {
        int index = fileName.lastIndexOf("\\");
        if (index > 0) {
            return fileName.substring(index + 1);
        }
        return fileName;
    }


     /**
     * Dato un file, trova la sua estensione
     *
     * @param fileName nome del file da controllare
     * @return estensione del file in input
     */
    public static String getExtension(String fileName) {
        int index = fileName.lastIndexOf(".");
        return (index >= 0) ? fileName.substring(index) : "";

        }
    
     
    
}
