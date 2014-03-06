/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ssterrain;

import java.util.ArrayList;
import Units.*;

/**
 *
 * @author John Goettsche
 * CS 383 Software Engineering
 */
public class Hex {
    private ArrayList<HexEdge> edges;
    private TerrainType terrainType;
    private ArrayList<ImprovedTerrainType> improvements;
    private int ID;
    
    public int getID(){
        return ID;
    }
    
    public Hex(){
        edges = new ArrayList<HexEdge>();
        improvements = new ArrayList<ImprovedTerrainType>();
    }
    
    public void addEdge(HexEdge newEdge){
        edges.add(newEdge);
    }
    
    public void setTerrainType(TerrainType newTerrainType){
        this.terrainType = newTerrainType;
    }
    
    public void addImprovement(ImprovedTerrainType newImprovement){
        improvements.add(newImprovement);
    }
    
    public void removeEdge(HexEdge deadEdge){
        edges.remove(deadEdge);
    }
    
    public TerrainType getTerrainType(){
        return terrainType;
    }
    
    public void removeImprovement(ImprovedTerrainType deadImprovement){
        improvements.remove(deadImprovement);
    }
    
    public ArrayList<ImprovedTerrainType> getImprovements(){
        return improvements;
    }
    
    public boolean checkIfCrossed(ArrayList<HexEdgeType> list){
        for(int l = 0; l < list.size(); l++){
            for(int e = 0; e < edges.size(); e++){
                if(edges.get(e).getEdgeType().equals(list.get(l)))return true;
            }
        }
        return false;
    }
    
    public ArrayList<HexEdgeType> getEdgeType(int edge){
        ArrayList<HexEdgeType> thisEdge = new ArrayList<HexEdgeType>();
        for(int e = 0; e < edges.size(); e++){
            if(edges.get(e).getEdge() == edge) thisEdge.add(edges.get(e).getEdgeType());
        }
        return thisEdge;
    }
    
    public double getMovementCost(Unit unit){
        double move = terrainType.getMovementCost(unit);
        double override = 100;
        if(improvements.size() > 0)
            for(int i = 0; i < improvements.size(); i++){   
                move += improvements.get(i).getMovementCost(unit);
                if(improvements.get(i).getMovementOverride(unit) > 0.0)
                    if(improvements.get(i).getMovementOverride(unit) < override) 
                        override = improvements.get(i).getMovementOverride(unit);
            }
        if(override > 0 && override < 100) move = override;
        return move;
    }
    
    public double getCombatMultiplier(Unit unit){
        double mult = 1;
        mult *= terrainType.getCombatMultiplier(unit);
        if(improvements.size() > 0)
            for(int i = 0; i < improvements.size(); i++)
                mult *= improvements.get(i).getCombatMultiplier(unit);
        return mult;
    }
}
