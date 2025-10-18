/*
 * Class: Graph.java
 * Name: Michael Masenheimer
 * Package: Project2 (No inheritence), relies on no other files
 * 
 * Purpose: This class represents a graph object, using an ArrayList<Integer>[] to
 * represent the connectivity of nodes between the graph, and also uses a boolean[] to
 * show which vertices are active or not. The class also comes with various common
 * data structures and algorithm functions, such as a connectivity check, DFS,
 * mutating the edges and vertices, among others. This class is used by the Map class
 * to check the relationship between territories.
 * 
 * public ArrayList<Integer>[] adjList; - vertex/list of vertices associated with it (edges)
 * public boolean[] active; - bool array of vertex ids which shows which are active and inactive
 * public int numVertices - int representation of the number of vertices in the graph
 * 
 * The Graph constructor takes in an integer number of vertices, and declares a fully connected
 * graph of n vertices, also setting each vertex to active. This will be changed by the map class
 * later when  declaring which vertices are not part of the current game.
 */


import java.util.ArrayList;
import java.util.List;

public class Graph {

    public ArrayList<Integer>[] adjList;
    public boolean[] active;
    public int numVertices;

    /*
    * Method: Graph
    * 
    * Purpose: Initializes a graph object
    * 
    * Pre-condition: None
    * 
    * Post-condition: A graph object is initialized
    * 
    * Return value: None
    * 
    * Parameters:
    *  - numVertces is an integer number of vertices in the graph
    */

    public Graph(int numVertices) {

        // Initialize the adj list with number of vertices
        this.adjList = new ArrayList[numVertices];
        this.active = new boolean[numVertices];
        this.numVertices = numVertices;

        for (int i = 0; i < numVertices; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int i = 0; i < numVertices; i++) {
            // Assume all vertices are active upon creation of the graph
            active[i] = true;
        }
    }

    /*
     * Method: getUnusedVertices
     * Purpose: Gets all of the unused vertices and returns them in a list
     * format
     * Pre-condition: There's a graph with some used and unused vertices
     * 
     * Post-condition: The unused vertices are found and returned
     * 
     * ReturnValue: List of unused vertices ids
     */

    public List<Integer> getUnusedVertices() {
        List<Integer> unused = new ArrayList<>();

        for (int i = 0; i < active.length; i++) {
            // Loop through the active vertices and find inactive ones
            if (active[i] == false) {
                unused.add(i);
            }
        }

        return unused;

    }

    /*
     * Method: isEdge
     * Purpose: Finds if there exists an edge between source and destination
     * 
     * Pre-condition: the source and destination variables are vertices
     * 
     * Post-condition: There exists or does not exist an edge between the two
     * vertices
     * 
     * Return value: true or false if the condition is met
     * 
     * Parameters:
     * 
     * source is an int vertex id
     * destination is an int vertex destination
     * 
     */

    public boolean isEdge(int source, int destination) {

        ArrayList<Integer> listVertices = adjList[source];

        for (int i = 0; i < listVertices.size(); i++) {
            // Go through the vertices source is connected to

            if (listVertices.get(i) == destination) {
                return true;
            }
        }
        return false;
    }

    /*
     * Method: addEdge
     * Purpose: to add an edge between two vertices
     * 
     * Pre-condtitions- None
     * 
     * Post-conditions- Adds a vertex between the two vertices
     * 
     * Return value: null
     * 
     * Parameters:
     * 
     * source is an int vertex id
     * destination is an int verted id
     */

    public void addEdge(int source, int destination) {

        ArrayList<Integer> listSource = adjList[source];

        listSource.add(destination);
        // Add a new edge between source and destination

        ArrayList<Integer> listDestination = adjList[destination];

        listDestination.add(source);
        // Add the same edge between destination and source

    }

    /*
     * Method: removeEdge
     * 
     * Purpose: remove an edge between two vertices
     * 
     * Pre-condition: None
     * 
     * Post-condition: Removes an edge between both vertices
     * 
     * Return value: None
     * 
     * Parameters:
     *  - source is a vertex id source
     *  - destination is a vertex id destination
     */

    public void removeEdge(int source, int destination) {

        ArrayList<Integer> listSource = adjList[source];

        for (int i = 0; i < listSource.size(); i++) {
            // Look for the destination vertex to be removed

            if (listSource.get(i) == destination) {
                listSource.remove(i);
                break;
            }
        
        }

        ArrayList<Integer> listDestination = adjList[destination];

        for (int i = 0; i < listDestination.size(); i++) {
            // Look for the source vertex to be removed

            if (listDestination.get(i) == source) {
                listDestination.remove(i);
                break;
            }
        }
    }
    /*
     * Method: isInGraph
     * Purpose: To tell if a vertex is in the graph and is active
     * 
     * Returns: true or false based on that condition
     */

    public boolean isInGraph(int vertex) {

        return active[vertex];
        // Return the bool value associated with activity of the vertex

    }

    /*
     * Method: removeVertex
     * Purpose: removes a vertex and its edges from the graph
     * 
     * Pre-condtion: The vertex is in the graph and connected
     * 
     * Post-condition: The vertex is removed
     * 
     * Returns: None
     * 
     * Parameters:
     * 
     *  - vertex is a vertex id
     * 
     */

    public void removeVertex(int vertex) {

        ArrayList<Integer> listAdjacancies = adjList[vertex];

        active[vertex] = false;
        // Set as inactive


        adjList[vertex] = new ArrayList<>();
        // Adjust the neighbors to be zero

        for (int i = 0; i < listAdjacancies.size(); i++) {
            // This gives me all of the vertices vertex was adjacent to

            ArrayList<Integer> newAdjacencies = adjList[listAdjacancies.get(i)];

            for (int j = 0; j < newAdjacencies.size(); j++) {
                if (newAdjacencies.get(j) == vertex) {
                    newAdjacencies.remove(j);
                    break;
                }
            }
        }
    }

    /*
     * Method: getAdjacent
     * 
     * Purpose: This function returns the adjacent vertices to the input vertex
     * 
     * Pre-condition: None
     * 
     * Post-condition: Returns the adjacent vertices
     * 
     * Returns: List of adjacent vertices
     * 
     * Parameters: verrtex is a vertex id
     */

    public List<Integer> getAdjacent(int vertex) {
        return adjList[vertex];
        // Return the adjacency list associated with this vertex
    }
    
    /*
     * Method: degree
     * Purpose: returns the degree (connectivity) of the input vertex
     */
    public int degree(int vertex) {
        return adjList[vertex].size();
        // Return the size of the adjacency list
    }

    /*
     * Method: connected
     * Purpose: returns if the graph is connected
     */

    public boolean connected() {
        return isConnected(adjList, active);
    }

    /*
     * Method: isConnected()
     * 
     * Purpose: returns if the graph is connected, by calling bfs function
     * 
     * Pre-condition: The graph is either connected or disconnected
     * 
     * Post-Condition: None
     * 
     * Return value: true or false if it is connected or not
     * 
     * Parameters theList is the connectivity list between nodes, active is
     * an array if a vertex id is active or not
     */

    public static boolean isConnected(ArrayList<Integer>[] theList, boolean[] active) {

        int n = active.length;
        boolean[] visited = new boolean[n];

        int start = -1;

        for (int i = 0; i < n; i++) {
            // Find an active vertex to start dfs on
            if (active[i] == true) {
                start = i;
                break;
            }
        }

        if (start == -1) return true;
        // If there are no active vertices, the graph is still connected

        dfs(start, theList, visited, active);

        for (int i = 0; i < n; i++) {

            if (active[i] == true && !visited[i]) {
                // If an active vertex is unvisited, return false

                return false;
            }
        }
        return true;
    }

    /*
     * Method: dfs
     * Purpose: runs dfs on a vertex and goes through the graph to determine
     * connectivity.
     * 
     * Pre-condition: None
     * 
     * Post-condition: None
     * 
     * Return value: None
     * 
     * Parameters: vertex is an id for a vertex
     * 
     * adjlist is the connectivity list, visited is a bool list of vertex ids, active is a boolean list
     * of true or false if the vertex is active or not
     */

    public static void dfs(int vertex, ArrayList<Integer>[] adjList, boolean[] visited, boolean[] active) {
        visited[vertex] = true;

        for (int neighbor : adjList[vertex]) {
            // For each neighbor in the current vertex, call dfs

            if (visited[neighbor] == false && active[neighbor] == true) {
                // Make sure the neighbor is not visited and it's an active vertex
                
                dfs(neighbor, adjList, visited, active);
            }

        }
    }

}
