/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import static util.confGenerator.round;

/**
 *
 * @author qiuyan
 */
public class sqlGenerator {
      public static void main(String[] args) {
        String outfilename;
        PrintWriter pw;
        int id=0;
     
        outfilename = "/Users/qiuyan/Documents/FYP_Experiment/serverInfileNames.txt";

        for(id=0;id<6204;id++){
            //System.pw.println(outfilename);
            try{
                pw = new PrintWriter(new FileOutputStream(outfilename, true), true);
                pw.println("/home/qiuyan/FYP/results/result"+id+".txt");

            }catch (IOException e) {
                e.printStackTrace();
            }   
                
            }//end of for(imid)
     
            
        }// end of for(numofdegree)
        
        
    }
    
