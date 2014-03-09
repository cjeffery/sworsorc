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
 * This is an experiment with packing/unpacking messages
 * 
 * Right now, this literally sends stuff in an array one string at a time. 
 * 
 * It is not efficient, but it's very easy to make new message types.
 * 
 * If we make sure to pack/unpack messages here, we can easy switch out
 * the logic and make it more efficient later!
 */

public class MessageUtils {
    
    //End of message (last element of any array):
    static String DONE = "donesignalthingy"; 
    
    //Opening string to tell receiver how to interpret message contents:
    static String CHAT = "chat"; //Normal chat message:
    static String DISCONNECT = "disconnect"; //Announcement:
    static String CONNECT = "connect"; //Announcement:
    
    static boolean debug = false;

    //Announce that user joined system:
    public static List<String> makeConnectionMessage(String handle){
        List<String> message = new ArrayList<>();
        message.add(CONNECT);
        message.add(handle);  
        return message;
    }
    public static void printConnectionMessage(PrintWriter write, List<String> array){
        write.println("User: " + array.get(1) + " has joined ");
    }
 
    
    //Normal chat message, just forward and print handle:
    public static List<String> makeChatMessage(String handle, String text){
        List<String> message = new ArrayList<>();
        message.add(CHAT);
        message.add(handle);
        message.add(text);
       
        return message;
    }
    public static void printChat(PrintWriter write, List<String> array){
        write.println(array.get(1) + ": " + array.get(2));
        
    }
     
    //Announce that user has disconnected (this isn't the message that says to disconnect)
    public static List<String> makeDisconnectMessage(String handle){
        List<String> message = new ArrayList<>();
        message.add(DISCONNECT);
        message.add(handle);  
        return message;
    }
    public static void printDisconnect(PrintWriter write, List<String> array){
        write.println("User: " + array.get(1) + " has disconnected.");
    } 
    

    //Send the array, ending with the DONE string:
    public static void sendMessage(PrintWriter write, List<String> message){
        if (debug) System.out.println("S!ending array: " + message);
        for (String s: message){
            write.println(s);
            write.flush();
            
            if (debug) System.out.println("!Sent array element:" + s);
        }
        write.println(DONE);
        write.flush();
        if (debug) System.out.println("!Done sending array");
    }
    
    //Fill an array, until we read the DONE string
    public static List<String> receiveMessage(BufferedReader reader){
        List<String> array = new ArrayList<>();
        String next = null;
        if (debug) System.out.println("!Receiving array");
        try {
            next = reader.readLine();
            if (next == null) {
                return null;
            }
            while (!next.equals(DONE)){
                array.add(next);
                if (debug) System.out.println("!Received array element:" + next);
                next = reader.readLine();
                if (next == null){
                    return null;
                }
            }
        }
        catch (Exception e) {
            System.err.println("Error reading array:" + e);
        }
        
        if (debug) System.out.println("!Done receiving array");
        return array;
    }
    

    
}
