/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chatclient;

/**
 *
 * @author John Goettsche
 */
public class Player {
    //temporary class to use as a placeholder until it is better defined for
    //gameplay.  currently only used with ChatClient.DataObject
    private String username;
    private String ip;
    private int id;
    
    public Player(){
        
    }
    
    public void setUsername(String newUsername){
        this.username = newUsername;
    }
    
    public void setIP(String newIP){
        this.ip = newIP;
    }
    
    public void setID(int newID){
        this.id = newID;
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getIP(){
        return ip;
    }
    
    public int getID(){
        return id;
    }
}
