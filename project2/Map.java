/*
 * Michael Masenheimer CSC 345 
 * Program #2: Bones Battle
 * Dr. McCann, Dr. Lynam, Jesse Chen, Moyeen Uddin, due October 16
 * 
 * Description:
 *
 * This program was written to ultimately help practice graph programming and
 * algorithmic thinking. In this project, I implement BFS on a self created graph
 * object. The files interact with some pre-written files by Dr. McCann, creating
 * a comprehensive "risk" game with java. In terms of data structures, the main one used
 * is a graph, followed by various ArrayLists and arrays for sub functionality.
 * 
 * Operations:
 * 
 * This program uses Java version 24, and the input is in the form of a user
 * click when the program is running, to select a square and attack other
 * squares. It can be ran from the terminal by compiling the code first
 * with javac *.java and then running java Bones
 * 
 * As far as I know, all possible random placements of inactive squares and player/
 * computer squares work with the supplied files, and to the best of my knowledge,
 * there are no UI and backend errors.
 */

 /*
 * Class: Map.java
 * Name: Michael Masenheimer
 * 
 * Package: Project2 (No inheritence), 
 * relies on Player.java, Territory.java, and Graph.java (Also Random)
 * 
 * Purpose: This class represents the map of territories in the game, represented both by
 * a 2D Territory array and a Graph object, which represents the connectivity of nodes within
 * the board. (Used to tell if an inactive node cannot be accessed as well)
 * 
 * As stated before, this class relies on Graph to hold the edge and activity data for each territory,
 * and this is accessed via the territory id. Random is used for generating random locations for the plpayer's
 * territores, and random inactive territories.
 * 
 * ROWS - number of rows in the graph
 * COLUMNS - number of columns in the graph
 * VICTIMS - number of inactive territories
 * NUMTEITORIES - number of total territories
 * OCCUPIED - number of active territories
 * theMap - Territory 2D array of player territories
 * gameGraph - Graph object showing connectivity and edges/active territories
 * MAXDICE - max number of dice per territory
 * players - List of players in the game, represented with player objects
 * 
 * The Map constuctor sets the player list, rows, cols, inactive territories, and the max number
 * of dice. It creates the game board with the map array, and creates territories for the board. It
 * calls various methods to initialize the game board, getting it ready for functionality.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map {

    public final int ROWS;
    public final int COLUMNS;
    public final int VICTIMS;

    public final int NUMTERRITORIES;
    // rows * columns

    public final int OCCUPIED;
    // numterritories - victims

    public Territory[][] theMap;
    public Graph gameGraph;
    // Control fields for territory activity and connectivity

    public final int MAXDICE;
    public ArrayList<Player> players;

    /*
     * Method: Map
     * Purpose: Initializes the Map class and creates a graph object representing the
     * current state of the graph. It also instantiates the constant and class fields
     * 
     * Pre-condition:
     *  - Graph is a separate class, Territory is also a separate class. Both of these
     *  need to work together to provide the Map class everything it needs to know about
     *  the current game state. This function sets that up
     * 
     * Post-condition: all fo the fields are set, and the Territory[][] array is filled, this
     * function calls the other methods that make the graph object, for example, and sets it up.
     * 
     * Return value: Returns nothing
     * 
     * Parameters:
     *  - players is an ArrayList of player objects, representing the players of the game
     *  - rows is a constant representing the number of rows in the board
     *  - columns is a constant representing the number of columns in the board
     *  - victims is a constant representing the number of inactive spots on the board
     *  - maxDice is the max number of dice a territory can have
     */

    public Map(ArrayList<Player> players, int rows, int columns, int victims, int maxDice) {
        
        this.ROWS = rows;
        this.COLUMNS = columns;
        this.VICTIMS = victims;
        this.NUMTERRITORIES = rows * columns;
        this.OCCUPIED = this.NUMTERRITORIES - this.VICTIMS;
        this.MAXDICE = maxDice;
        this.players = players;
        

        this.theMap = new Territory[rows][columns];

        for (int i = 0; i < this.ROWS; i++) {
            // Make new territory for each item in the territories array

            for (int j = 0; j < this.COLUMNS; j++) {

                this.theMap[i][j] = new Territory(this);
                this.theMap[i][j].setIdNum(i * this.COLUMNS + j);
                // Set a unique id number for each territor
            }
        }

        this.gameGraph = constructGraph(rows, columns, victims);
        // Make the graph corresponding to the current gamestate

        partitionTerritories();
        // Distribute territories to players

        distributeDice();
        // Distribute the dice to each territory
    }

    /*
     * Methods: Getters and setters
     * Purpose: To be able to extract and update field information from
     * the class.
     * 
     * Pre-condition: the fields have already been set
     * 
     * Post-condition: The fields are updated or returned
     */

    public Territory[][] getMap() {
        return this.theMap;
        // Return map of territories
    }

    public Graph getGraph() {
        return this.gameGraph;
        // Return the game graph
    }

    public Territory getTerritory(int row, int column) {
        return this.theMap[row][column];
        // Return the territory associated with row, col
    }

    public int getTerritoryId(int row, int column) {

        Territory current = this.theMap[row][column];
        return current.getIdNum();
        // Return the id associated with territory at row, column

    }

    /*
     * Method: countTerritories
     * Purpose: Counts the number of territories associated with the input Player
     * 
     * Pre-conditions:
     *  - The player has actual territories in the game
     *  - theMap[][] has been instantiated with territories
     * 
     * Post-condition: None
     * 
     * Return value: integer representing how many territories are associated with
     * the player
     * 
     * Parameters: player is a Player object, which a certain amount of territories
     * are tied to.
     */

    public int countTerritories (Player player) {
        int territories = 0;

        for (int i = 0; i < this.theMap.length; i++) {

            for (int j = 0; j < this.theMap[0].length; j++) {
                // Loop through every Territory in the map

                if (this.theMap[i][j] != null && this.theMap[i][j].getOwner() == player) {
                    // Make sure the territory is not null (the territory actually exists)
                    territories++;
                }
            }
        }

        return territories;
    }
    
    /*
     * Method: countDice
     * 
     * Purpose: Counts the number of dice associated with an input player's
     * territories
     * 
     * Pre-conditions:
     *  - The player has actual territories in the game
     *  - the map[][] has been instantiated with territories
     * 
     * Post-condition: None
     * 
     * Return value: integer representing how many dice a player has
     * accross all territories
     */

    public int countDice(Player player) {

        int numDice = 0;

        for (int i = 0; i < this.theMap.length; i++) {

            for (int j = 0; j < this.theMap[0].length; j++) {
                // Loop through each territory and check if it's a player territory

                if (this.theMap[i][j] != null && this.theMap[i][j].getOwner() == player) {
                    // Make sure the territory isn't null and it's a player's

                    numDice += this.theMap[i][j].getDice();
                }
            }
        }
        return numDice;
    }

    /*
     * Method: getPropertyOf
     * 
     * Purpose: This method generates a list of territories associated with
     * the input player
     * 
     * Pre-condition:
     *  - The player has active territories already distributed
     *  - The map[][] is instantiated with territories
     * 
     * Post-condtition:
     *  - None
     * 
     * Return value: Array list of territories associated with the input player
     */

    public ArrayList<Territory> getPropertyOf(Player player) {

        ArrayList<Territory> playerProperties = new ArrayList<>();

        for (int i = 0; i < this.theMap.length; i++) {

            for (int j = 0; j < this.theMap[0].length; j++) {
                // Loop through each territory

                if (this.theMap[i][j] != null && this.theMap[i][j].getOwner() == player && this.theMap[i][j] != null) {
                    // Make sure the territory isn't null and it is associated with the player

                    playerProperties.add(this.theMap[i][j]);
                }
            }
        }

        return playerProperties;
    }

    /*
     * Method: getNeighbors
     * 
     * Purpose: grabs the neigbors of a specific cell
     * 
     * Pre-condition:
     *  - There are or aren't nodes tangent to the current cell
     *  - The cell exists in the graph
     * 
     * Post-condition:
     *  - None
     * 
     * Return value: ArrayList<Territory> of territories neigboring the
     * input cell
     * 
     * Parameters:
     *  - cell is a terrritory to be scanned for neighbors
     */

    public ArrayList<Territory> getNeighbors(Territory cell) {

        ArrayList<Territory> territoryNeighbors = new ArrayList<>();

        int id = cell.getIdNum();
        List<Integer> curr = gameGraph.getAdjacent(id);
        // Gives me the id of all the neightbors

        for (int i = 0; i < curr.size(); i++) {

            int neighborId = curr.get(i);
            int row = neighborId / this.COLUMNS;
            int col = neighborId % this.COLUMNS;
            // Grab the row and col for each neighbor

            Territory neighbor = this.theMap[row][col];

            if (neighbor != null) {
                territoryNeighbors.add(neighbor);
                // Add the neighbor if it's an active territory
            }
        }

        return territoryNeighbors;
    }

    /*
     * Method: getEnemyNeighbors
     * 
     * Purpose: Gets the neighbors of an input cell that are
     * not owned by the input cell's Player
     * 
     * Pre-condition:
     *  - There are or aren't nodes tangent to the current cell
     *  - The cell exists in the graph
     * 
     * Post-condition: none
     * 
     * Parameters: cell is a territory
     */

    public ArrayList<Territory> getEnemyNeighbors(Territory cell) {

        ArrayList<Territory> enemyNeighbors = new ArrayList<>();

        int id = cell.getIdNum();
        List<Integer> curr = gameGraph.getAdjacent(id);
        // Grab the id of the input cell, and its adjacent territories

        for (int i = 0; i < curr.size(); i++) {

            int neighborId = curr.get(i);

            int row = neighborId / this.COLUMNS;
            int col = neighborId % this.COLUMNS;
            // Get the row and col of each neighbor

            Territory neighbor = this.theMap[row][col];

            if (neighbor != null && cell.getOwner() != neighbor.getOwner()) {
                // Make sure the neigboring territories are both active and not owned
                // by the currrent player

                enemyNeighbors.add(neighbor);
            }
        }
        return enemyNeighbors;
    }

    /*
     * Method: partitionTerriories
     * 
     * Purpose: This method assigns territories to play
     * 
     * Pre-condition:
     * The territories have been made already, but not assigned
     * 
     * Post-condition: each player will get assigned an equal number of
     * random territories in the graph
     */

    private void partitionTerritories() {

        int numPlayers = this.players.size();
        ArrayList<Integer> availableTerritories = new ArrayList<>();
        // Make a list of available territories to choose randomly from

        for (Integer i = 0; i < this.ROWS * this.COLUMNS; i++) {

            if (this.gameGraph.isInGraph(i)) {
                // Add to available territories if it's in the graph
                availableTerritories.add(i);
            }
        }

        int numTerritoriesPerPlayer = availableTerritories.size() / numPlayers;
        // Number of territories each player will have
        Random rand = new Random();

        for (int i = 0; i < numPlayers; i++) {
            // For each player, assign random positions

            Player currentPlayer = players.get(i);

            for (int j = 0; j < numTerritoriesPerPlayer; j++) {

                if (availableTerritories.isEmpty()) {
                    break;
                }

                int randomIndex = rand.nextInt(availableTerritories.size());
                int territoryId = availableTerritories.remove(randomIndex);
                // Pick a random territory index

                int row = territoryId / COLUMNS;
                int col = territoryId % COLUMNS;

                Territory curr = this.theMap[row][col];
                // Grab the current territory to set the owner to

                if (curr != null) {
                    curr.setOwner(currentPlayer);
                    // Set the owner if it's an active square
                }
            }
        }
    }

    /*
     * Method: distributeDice
     * 
     * Purpose: distributes dice to each territory, random
     * 
     * Pre-condition: The territories have been made already
     * 
     * Post-conditions: The dice are assigned to territories upon
     * program load
     */

    private void distributeDice() {
        Random rand = new Random();

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);

            ArrayList<Territory> owned = getPropertyOf(player);
            // Get the owned properties of the current player

            int numterritories = owned.size();
            int numDice = 3 * numterritories;
            // Assign 3 times number territories for the dice

            for (int j = 0; j < numterritories; j++) {
                owned.get(j).setDice(1);
                // Set the dice for each of the owned territories
            }

            int remaining = numDice - numterritories;

            while (remaining > 0) {
                Territory curr = owned.get(rand.nextInt(numterritories));
                // Pick a random territory

                if (curr.getDice() < MAXDICE) {

                    curr.setDice(curr.getDice() + 1);
                    remaining -= 1;
                }
            }
        }
    }

    /*
     * Method: countConnected
     * 
     * Pupose: This method uses depth-first search to check the largest
     * connected territory of the player
     * 
     * Pre-condition: the graph is made and the players have been assigned
     * to their fields
     * 
     * post-condition: The function returns the result of dfs on a player
     * territory
     * 
     * Return value: int of the largest connected island of territories
     * 
     * Parameters: player is a Player object
     */

    public int countConnected(Player player) {

        boolean[][] visited = new boolean[ROWS][COLUMNS];
        // Which territories have been visited already
        int largest = 0;

        for (int r = 0; r < ROWS; r++) {

            for (int c = 0; c < COLUMNS; c++) {
                // Vitis each index

                Territory t = this.theMap[r][c];

                if (t != null && t.getOwner() == player && !visited[r][c]) {
                    // Make sure the territory is active and not visited

                    int size = dfs(t, visited, player);

                    if (size > largest) {
                        // Return the largest size
                        largest = size;
                    }
                }
            }
        }

    return largest;
    }

    /*
     * Method: dfs
     * 
     * Purpose: This function runs depth first search on an input territory,
     * which is a helper function to check for the largest island of territories
     * by a player
     * 
     * Pre-condition: The proper parameters have been filled out
     * 
     * Post-condition: The correct territories have been visited and analyzed.
     * 
     * Return value: int of number of territories per that player in the island
     * 
     * Parameters: currentTerritory is a territory
     * 
     */

    private int dfs(Territory currentTerritory, boolean[][] visited, Player player) {

        int row = currentTerritory.getRow();
        int col = currentTerritory.getCol();
        // Grab row and col of the current territory

        visited[row][col] = true;
        int count = 1;

        ArrayList<Territory> neighbors = getNeighbors(currentTerritory);
        // Grab the neighbors of the current territory

        for (int i = 0; i < neighbors.size(); i++) {

            Territory theNeighbors = neighbors.get(i);

            if (theNeighbors != null && theNeighbors.getOwner() == player) {
                // Make sure the neighbors of the current territory is not null

                int nr = theNeighbors.getRow();
                int nc = theNeighbors.getCol();

                // Get the row and the col of the current neighbors' territoy

                if (!visited[nr][nc]) {
                    // If the territy isn't visited, recurse on it
                    count += dfs(theNeighbors, visited, player);
                }
            }
        }
        return count;
    }

    /*
     * Method: constructGraph()
     * 
     * Purpose: sets up a graph object representing the current game state
     * 
     * Pre-condition: none
     * 
     * Post-condition: the map constructor creates a graph object which is set as the
     * main graph
     * 
     * Return value: Returns the graph object
     * 
     * Parameters:
     * 
     *  - rows: an int with the number of rows in the graph
     *  - cols: an int with the number of cols in the graph
     *  - victims: the number of inactive squares in the graph
     */


    public Graph constructGraph(int rows, int cols, int victims) {

        Graph currGraph = new Graph(rows * cols);

        for (int r = 0; r < rows; r++) {

            for (int c = 0; c < cols; c++) {
                // Iterate over the rows and columns of th  new graph object

                int id = r * cols + c;

                if (r > 0) currGraph.addEdge(id, (r - 1) * cols + c); // up
                // Add edge up

                if (r < rows - 1) currGraph.addEdge(id, (r + 1) * cols + c);
                // Add edge down

                if (c > 0) currGraph.addEdge(id, r * cols + (c - 1));
                // Add edge left

                if (c < cols - 1) currGraph.addEdge(id, r * cols + (c + 1));
                // Add edge right
            }
        }

        Random rand = new Random();
        ArrayList<Integer> activeList = new ArrayList<Integer>();
        // Make a new ArrayList of the active squares for recording

        for (int i = 0; i < rows * cols; i++) {
            // Set every node to an active vertex
            activeList.add(i);
        }

        int removed = 0;

        while (removed < victims && !activeList.isEmpty()) {

            int index = rand.nextInt(activeList.size());
            int vertex = activeList.remove(index);
            // Set up removal of random vertices in the graph

            currGraph.removeVertex(vertex);

            int rr = vertex / cols;
            int cc = vertex % cols;
            // Grab the row and col
        
            removed++;
        }

        return currGraph;

    }

}