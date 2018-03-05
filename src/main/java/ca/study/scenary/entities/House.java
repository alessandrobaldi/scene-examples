/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.study.scenary.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author alessandro
 */

public class House {
    private List<House> neighbors = new ArrayList<House>();
    private List<Mosquito> mosquitos = new ArrayList<Mosquito>();
    private List<Eggs> eggs = new ArrayList<Eggs>();
    private List<Agents> agents= new ArrayList<Agents>();       
    private boolean focus = false;
    private boolean trap = false;
    private boolean activefocus = false;
    private String name = "";
    private int houseId;
    private static int count = 0;

    House(String nameHouse) {
    	this.houseId = count++;
        name=nameHouse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActivefocus() {
        return activefocus;
    }

    public void setActivefocus(boolean activefocus) {
        this.activefocus = activefocus;
    }
    
    public void removeMosquito(Mosquito mosquitoRemove)
    {
        this.getMosquitos().remove(mosquitoRemove);
    }
    
    public void changeMosquito(Mosquito mosquitoChange)
    {
       List<House> neighborHouse = this.getNeighbors();
       int chooseHouse = randomize(0, neighborHouse.size() - 1);
       House newHouse = neighborHouse.get(chooseHouse);
       List<Mosquito> newMosquitoAdd = newHouse.getMosquitos();
       mosquitoChange.setControlfly(true);
       this.getMosquitos().remove(mosquitoChange);
       newMosquitoAdd.add(mosquitoChange);
       //System.out.println("Mosquito Flying");
       
    }
    
            public void doActions(Mosquito mosquitoChange)
    {
       List<House> neighborHouse = this.getNeighbors();
       int chooseHouse = randomize(0, neighborHouse.size() - 1);
       House newHouse = neighborHouse.get(chooseHouse);
       List<Mosquito> newMosquitoAdd = newHouse.getMosquitos();
       mosquitoChange.setControlfly(true);
       mosquitoChange.setControleggs(true);
       newHouse.addEggs();
       
       this.getMosquitos().remove(mosquitoChange);
       newMosquitoAdd.add(mosquitoChange);
       //System.out.println("Mosquito Flying");
    }
      
    public void addEggs()
    {
        Eggs egg = new Eggs();
        eggs.add(egg);
    }
    
    public void callAgent()
    {
       House inHouse = this;
       inHouse.newAgent();
        List<Eggs> eggsInHouse = inHouse.getEggs();
        eggsInHouse.clear();
        List<Mosquito> mosquitoInHouseant = inHouse.getMosquitos();
              mosquitoInHouseant.clear();
              inHouse.setActivefocus(false);
        House houseant = inHouse;
        for (int visited = 0; visited < 4; visited++) {
            List<House> neighborHouse = houseant.getNeighbors();
            int chooseHouse = randomize(0, neighborHouse.size() - 1);
            
             House newHouse = neighborHouse.get(chooseHouse);
             houseant.removeAgent();
             newHouse.newAgent();
              eggsInHouse = newHouse.getEggs();
              eggsInHouse.clear();
              List<Mosquito> mosquitoInHouse = newHouse.getMosquitos();
              mosquitoInHouse.clear();
              newHouse.setActivefocus(false);
                        houseant = newHouse;
                        System.out.println("Visited house by agent");
        }
        houseant.removeAgent();
    }
  
    void addNeighborhood(House house) {
        this.neighbors.add(house);
    }

    void addTrap() {
    this.trap=true;
    }

    void addFocus() {
    this.focus=true;
    this.activefocus=true;
    }

    public void addMosquito(String houseName, int houseId) {
        Mosquito mosquito = new Mosquito(houseName, houseId);
        mosquitos.add(mosquito);
    }
    

    public List<House> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<House> neighbors) {
        this.neighbors = neighbors;
    }

    public List<Mosquito> getMosquitos() {
        return mosquitos;
    }

    public void setMosquitos(List<Mosquito> mosquitos) {
        this.mosquitos = mosquitos;
    }

    public List<Eggs> getEggs() {
        return eggs;
    }

    public void setEggs(List<Eggs> eggs) {
        this.eggs = eggs;
    }

    public List<Agents> getAgents() {
        return agents;
    }

    public void setAgents(List<Agents> agents) {
        this.agents = agents;
    }

    public boolean isFocus() {
        return focus;
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }

    public boolean isTrap() {
        return trap;
    }

    public void setTrap(boolean trap) {
        this.trap = trap;
    }

    void newAgent() {
        Agents agent = new Agents();
        this.agents.add(agent);
    }

    void removeAgent(){
        this.agents.remove(0);
    }
    
    public int randomize(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
    
    public String toString(){
    	return "id: " + this.getHouseId() + " name; " + this.getName();
    }

	public int getHouseId() {
		return houseId;
	}
}
