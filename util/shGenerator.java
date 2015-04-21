/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author qiuyan
 */
public class shGenerator {
        public static void main(String[] args) {
        String outfilename;
        PrintWriter pw;
        int id;
        int confid=0;

        // 12 groups each with 517 experiments inside to run
        for(id=0;id<12;id++){
            
                outfilename = "/Users/qiuyan/git/koverlap/mysh/run-"+id+".sh";
                
            //System.pw.println(outfilename);
            for(int i=0;i<517;i++){
            try{
                pw = new PrintWriter(new FileOutputStream(outfilename, true), true);
                //java -cp /home/w/wuqiu/prog/ app.Simulation /home/w/wuqiu/conf/conf1.conf
                pw.println("java -cp /home/w/wuqiu/prog/ app.Simulation /home/w/wuqiu/conf/conf"+confid+".conf");   
                confid++;
            }catch (IOException e) {
                e.printStackTrace();
            }   
                
            }//end of for(imid)
                  
        }// end of for(numofdegree)
  
    }
  
}
