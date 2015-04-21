/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

/**
 *
 * @author qiuyan
 */
public class confGenerator {

    public static void main(String[] args) {
        String outfilename;
        PrintWriter pw;
        int numofdegree;
        int id=3102;  // confid as well as resultid
        int imid;//influence matrix id (0-281)
        double degree=0;
  
        // 11*282=3102 conf files for each type
        for(numofdegree=0;numofdegree<11;numofdegree++){
            for(imid=0;imid<282;imid++){
                outfilename = "/Users/qiuyan/git/koverlap/myconf/conf"+id+".conf";
            //System.pw.println(outfilename);
            try{
                pw = new PrintWriter(new FileOutputStream(outfilename, true), true);
                pw.println("periods=100");
                pw.println("runs=100");
                pw.println("numOrgs=100");
                pw.println("N=16");
                pw.println("numSubOrgs=2");
                pw.println("reportLevel=summary");
                pw.println("degree="+degree);
                pw.println("mgtType=ACTIVE");//manually switch between RUB and ACTIVE
                pw.println("influenceMatrix=/home/w/wuqiu/im/im"+imid+".txt");
                pw.println("outfile=/home/w/wuqiu/result/result"+id+".txt");
                pw.println("debug=false");
                id++;


            }catch (IOException e) {
                e.printStackTrace();
            }   
                
            }//end of for(imid)
                 
            degree = degree+0.1;
            degree = round(degree,1);
            System.out.println(degree);
            
        }// end of for(numofdegree)
        
        
    }
    
    public static double round(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();

    long factor = (long) Math.pow(10, places);
    value = value * factor;
    long tmp = Math.round(value);
    return (double) tmp / factor;
}
        
    
    
}
