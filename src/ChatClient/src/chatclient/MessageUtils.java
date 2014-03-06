/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chatclient;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * I made this to experiment with some messaging utility functions
 */

public class MessageUtils {
    
    static String DONE = "donesignalthingy";
    
    static boolean debug = false;
    
    public static void sendArray(PrintWriter write, List<String> array){
        if (debug) System.out.println("Sending array: " + array);
        for (String s: array){
            write.println(s);
            write.flush();
            
            if (debug) System.out.println("Sent array element:" + s);
        }
        write.println(DONE);
        write.flush();
        if (debug) System.out.println("Done sending array");
    }
    
    public static List<String> receiveArray(BufferedReader reader){
        List<String> array = new ArrayList<>();
        String next = null;
        if (debug) System.out.println("Receiving array");
        try {
            next = reader.readLine();
            while (!next.equals(DONE)){
                array.add(next);
                if (debug) System.out.println("Received array element:" + next);
                next = reader.readLine();
            }
        }
        catch (Exception e) {
            System.err.println("Error reading array:" + e);
        }
        
        if (debug) System.out.println("Done receiving array");
        return array;
    }
    

    
}
