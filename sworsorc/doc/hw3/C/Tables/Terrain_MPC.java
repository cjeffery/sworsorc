
// package may be in different name
package terrain_chart;

// may be in different name
public class Terrain_Chart {


    // the main() is not used here
    public static void main(String[] args) {
        
    
        
    }

    // Requires to pass certain varaiables to get a return Movement Points Cost
    static double MPC(String Terrain, String Racial, Boolean Character, Boolean Swamp_Creatures) {
        
        double MPC = 0; // Movement Point Cost

        
        
        
        switch (Terrain) {
            
            case "Blasted_hex":
                if (Racial == "Magic_Users") 
                    MPC = 2;
                
            
                else if (Racial == "Fighters") 
                    MPC = 3;
                
            
                else 
                    MPC = 100; // Impassiable
                
            
            case "Bridge":
                MPC = 1;
                
            case "Broken":
                MPC = 2;
                    
            case "City":
                if (Character == true) 
                    MPC = 0.5;
                
                else 
                    MPC = 1;
                
            case "Clear":
                MPC = 1;
                
            case "Cultivated":
                if (Character == true) 
                    MPC = 0.5;
                
                else 
                    MPC = 1;  
                
            case "Dragon_Tunnel_Complex":
                if (Racial == "Dragons")
                    MPC = 1;
                
                else 
                    MPC = 100; // Impassiable
               
            case "Forest":
                if (Racial == "Elves")
                    MPC = 2;
                else if (Racial == "Spiders")
                    MPC = 1;
                else 
                    MPC = 3;
                
            case "Glacier":
                if (Character == true)
                    MPC = 5;
                else {
                
                    if (Racial == "Killer_Peguins")
                        MPC = 1;
             
                    else 
                        MPC = 100; // Impassiable 
                }
            
            case "Karoo":
                if (Racial == "Cronks")
                    MPC = 1;
                else 
                    MPC = 2;
                
            case "Moat":
                if (Swamp_Creatures == true)
                    MPC = 2;
                
                else if (Racial == "Killer_Peguins")
                    MPC = 1;
                
                else 
                    MPC = 100; // Impassable
                
            case "River":
                if (Swamp_Creatures == true)
                    MPC = 2;
                
                else if (Racial == "Killer_Peguins")
                    MPC = 1;
                
                else 
                    MPC = 100; // Impassable  
                
                
            case "Lake":
                if (Swamp_Creatures == true)
                    MPC = 2;
                
                else if (Racial == "Killer_Peguins")
                    MPC = 1;
                
                else 
                    MPC = 100; // Impassable   
                
            case "Mountain":
                if (Racial == "Orcs" || Racial == "Dwarrows")
                    MPC = 2;
                else 
                    MPC = 3;
                
            case "Stream_hexside":
                MPC = -1;
                
            case "Swamp":
                
                
                if (Swamp_Creatures == true) 
                    MPC = 1;
                
                else {
                    if (Racial == "Cronks")
                        MPC = 2;
                    else
                        MPC = 3;
                    }
                
            case "Trail":
                
                MPC = 1;
                
            case "Vortex":
                
                MPC = 100; // Impassiable
                
            case "Wall_hexside":
                
                MPC = 100; // Impassiable
                
            case "Woods":
                
                if (Racial == "Elves")
                    MPC = 1;
                else 
                    MPC = 2;
                
                
        } // End of Switch
        
        
        
        return MPC;
        
        
    } // End of MPC
    
    
    
    
}




