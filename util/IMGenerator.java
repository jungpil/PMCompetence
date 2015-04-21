/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Qiuyan
 */
public class IMGenerator {

    public static void main(String[] args) {
        
        
        int n = 16;
        int id = 0;
       
       //k from 0 to n-1 
        for(int k=0;k<1;k++){
            // generate 20 im for each level of k
            for(int num=0;num<1;num++){
                String text = "";

        ArrayList<Integer> list2 = new ArrayList<Integer>();
        
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> list = new ArrayList<Integer>();
            for (int x = 0; x < n; x++) {
                if (x != i) {
                    list.add(new Integer(x));
                }
            }
            Collections.shuffle(list);
            list2.clear();
            for (int y = 0; y < k; y++) {
                list2.add(list.get(y));
                
            }
            //System.out.println(list2.size());
            
            
            for (int j = 0; j < n; j++) {
                
                if(j==i){
                    text=text+"x";
                }else if(list2.contains(j)){
                    text=text+"x";
                }else{
                    text=text+"o";
                }
                if(j!=n-1){
                    text=text+",";
                }else{
                    text=text+"\n";
                }

            }
        }

        try {
            //File file = new File("/Users/qiuyan/git/koverlap/inf/.txt"+n+"k"+k+".txt");
            File file = new File("/Users/qiuyan/git/koverlap/myim/im"+id+".txt");
            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            output.write(text);
            output.close();
            id++;
        } catch (IOException e) {
            e.printStackTrace();
        }
            
            }
        
            
        }
        
    }
}