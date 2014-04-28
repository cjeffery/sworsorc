package systemServer;

/**
 *
 * @author John Goettsche
 * University of Idaho
 */
public class ClientData {
    private String ipAddress;
    private String username;
    private boolean done;
    
    public ClientData(){
        done = false;
    }
    
    public void setIPAddress(String newIPAddress){
        this.ipAddress = newIPAddress;
    }
    
    public void setUsername(String newUsername){
        this.username = newUsername;
    }
    
    public void setDone(){
        done = true;
    }
    
    public String getIPAddress(){
        return ipAddress;
    }
    
    public String getUsername(){
        return username;
    }
    
    public boolean isDone(){
        return done;
    }
}
