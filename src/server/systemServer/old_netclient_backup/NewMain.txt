package systemServer;

/**
 *
 * @author John Goettsche
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IDFactory factory = IDFactory.getInstance();
        System.out.println(factory.getID());
        IDFactory factory2 = IDFactory.getInstance();
        System.out.println(factory2.getID());
    }
    
}
