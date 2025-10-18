/*
 * Class: Territory.java
 * Name: Michael Masenheimer
 * 
 * Package: Project2 (No inheritence), 
 * relies on Player.java, and Map.java
 * 
 * Purpose: This class represents a Territory within the graph. It is responsible for
 * setting fields specific to the territory, including dice, id, and owner
 * 
 * This class relies on the Player class and the Map class, to help uniquely identify
 * territories
 * 
 * dice: number of dice in the territory
 * idNum: the number associated with the territory
 * owner: the player assigned to each territory 
 * map: the map class associated with each territory 
 * 
 * The Map constuctor sets the player list, rows, cols, inactive territories, and the max number
 * of dice. It creates the game board with the map array, and creates territories for the board. It
 * calls various methods to initialize the game board, getting it ready for functionality.
 */

public class Territory {

    public int dice;
    public int idNum;
    public Player owner;
    public Map map;

    /*
     * Method: Territory
     * 
     * Purpose: Sets the fields for the Territory class
     * 
     * Pre-condition: None
     * 
     * Post-condition: Sets all the fields
     * 
     * Return value: none
     * 
     * Parameters: map is a Map objects
     */

    public Territory(Map map) {
        this.map = map;
        this.owner = null;
        this.idNum = -1;
        // Set the fields
    }

    /*
     * Method: Territory
     * 
     * Purpose: Sets the fields for the Territory class
     * 
     * Pre-condition: None
     * 
     * Post-condition: Sets all the fields
     * 
     * Return value: none
     * 
     * Parameters: map is a Map object, owner is the player associated with
     * the territory, dice is the number of dice per territory, idnum is the
     * id number associated with the territory
     */

    public Territory(Map map, Player owner, int dice, int idNum) {
        this.dice = dice;
        this.idNum = idNum;
        this.owner = owner;
        this.map = map;
    }

    /*
     * Method: getters and setters
     * 
     * Purpose: get and update information from the fields
     * 
     * Pre-condition: None
     * 
     * Post-condition: None
     * 
     * Return value: the field if it is a getter
     * 
     * Paramters: whatever the setters might have
     */
    public int getDice() {
        // Get the dice
        return dice;
    }

    public int getIdNum() {
        // Return the idNum
        return idNum;
    }

    public Map getMap() {
        // Return the Map
        return map;
    }

    public Player getOwner() {
        // Return the owner of the territories
        return owner;
    }

    public void setDice(int numDice) {
        // Set the number of dice
        this.dice = numDice;
    }

    public void setIdNum(int newId) {
        // Set the id for the territory
        this.idNum = newId;
    }

    public void setOwner(Player newOwner) {
        // Set the owner for the territory
        this.owner = newOwner;
    }

    public int getRow() {
        // Get the row of the territory
        return idNum / map.COLUMNS;
    }

    public int getCol() {
        // Get the column of the territory
        return idNum % map.COLUMNS;
    }
    
}
