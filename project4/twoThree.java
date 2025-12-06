
import java.util.ArrayList;
import java.util.List;

/*
 * Class: twoThree.java
 *
 * Name: Michael Masenheimer
 * 
 * Package: Project4 (No inheritence), 
 * relies on no other files, but needs arrayList and List java imports
 * 
 * Purpose: This class (and subclasses) are responsible for holding information
 * about each word in the input text. The tree is represented as a teo three tree
 * where data gets inserted and then bubbled up. This way growth happens at the root.
 * A few methods which I'll go into more detail later involve inserting and printing
 * and traversing.
 * 
 * The only public constant/variable is a Node object which represents
 * the root of the tree
 * 
 * There is only one constructor, which when a twoThree object is made, it
 * instantiates the root to be null. From there, data can be inserted.
 */

public class twoThree {

    private Node root;

    /*
    * Class: Node.java
    *
    * Name: Michael Masenheimer
    * 
    * Package: Project4 (No inheritence), 
    * relies on no other files, but needs arrayList and List java imports
    * 
    * Purpose: This class represents a single "node" in the tree. It stores
    * the location of each word (depending on if it is a two or three node) in
    * an arraylist as well as if the node itself is a two node.
    * 
    * firstWord and Secondword are strings which are words from the input text.
    * firstLocation, secondLocation are lists to store the locations of the words 
    * (since words can have multiple locations)
    * 
    * left, middle, right are the children of the current node
    * isTwoNode is a boolean flag to tell the program if it is a two or three node
    * 
    * There are two constructors, one is for when we are making a completely new node,
    * so it needs the location, and the second will be used for when we split later on,
    * the locations are already known.
    */

    public static class Node {
        String firstWord, secondWord;
        // Data values for the node (Words from the text)

        List<Location> firstLocation;
        List<Location> secondLocation;

        Node left, middle, right;
        // Children (There will be a middle child if it is a three node)

        boolean isTwoNode;
        // True or false if the node is a two node

        /*
        * Method: Node constructor
        * Purpose: Initializes the fields for Node class
        * 
        * Pre-condition:
        *  - The fields are not instantiated yet
        * 
        * Post-condition: 
        * - The fields are instantiated
        * 
        * Return value: Returns nothing
        * 
        * Parameters:
        *  word is the word, paragraph is an int representing the paragraph it is at
        *  and lline is an int representing the line it is at
        */

        Node(String word, int paragraph, int line) {

            this.firstWord = word;
            this.isTwoNode = true;
            // Default node is a two node

            this.firstLocation = new ArrayList<>();
            this.firstLocation.add(new Location(paragraph, line));
            // Add the paragraph and the line to the list of locations upon node creation 
        }

        /*
        * Method: Node constructor
        *
        * Purpose: Initializes the fields for Node class for splitting only
        * 
        * Pre-condition:
        *  - The fields are not instantiated yet
        * 
        * Post-condition: 
        * - The fields are instantiated
        * 
        * Return value: Returns nothing
        * 
        * Parameters:
        *  word is the word, since everything else is already known
        */

        Node(String word) {
            // This constructor is for when we split the items in the tree (since we don't know what the line or paragraph is)
            this.firstWord = word;
            this.firstLocation = new ArrayList<>();
            this.isTwoNode = true;
        }
    }

    /*
    * Class: Location.java
    *
    * Name: Michael Masenheimer
    * 
    * Package: Project4 (No inheritence), 
    * relies on no other files
    * 
    * Purpose: This simple class houses two data points about a word, the line and The
    * paragraph, which are both integers. This class simply stores these in an object,
    * for clarity.
    * 
    * paragraph, line are ints representing the location of the word
    * 
    * The constructor initializes the fields
    */

    public static class Location {
        int paragraph;
        int line;
        // These will show us where the word was found

        public Location(int paragraph, int line) {
            this.paragraph = paragraph;
            this.line = line;
            // Initialize fields
        }
    }

    /*
     * Method: twoThree
     *
     * Purpose: Initializes the root to null.
     * This is the constructor for the twoThree class
     */

    public twoThree() {
        this.root = null;
        // Init tree with a null root
    }

    /*
     * Method: insert
     *
     * Purpose: calls a helper insert function to insert a word into the tree.
     * This version of insert takes in a word and a paragraph and a line, and calls
     * the helper witht the current root. I did this just for clarity, even though
     * I probably could have combined this into the helper. Although for recursion
     * purposes, I think it's a good idea to start at the very root of the tree.
     * 
     * Pre-condition:
     *  - The item isn't inserted yet
     * 
     * Post-condition: 
     * - the item is inserted and bubbled up accordingly
     * 
     * Return value: Returns nothing
     * 
     * Parameters:
     *  - word paragraph line are all data points about the word
     */

    public void insert(String word, int paragraph, int line) {
        this.root = insert(this.root, word, paragraph, line);
        // Call the insert function on the current root, inserting the word
    }

    /*
     * Method: insert (v2)
     *
     * Purpose: This function takes in all of the data about a word and
     * recursively finds where it needs to be inserted, it bubbles up accordingly,
     * by that I mean depending on the type of node, different node sizes will require
     * a different kind of split and bubble up. The function returns the node where the item was
     * inserted at. It also makes sure to keep track of locations in accordance with the words
     * since many locations can map to one word here.
     * 
     * Pre-condition:
     *  - The item isn't inserted yet
     * 
     * Post-condition: 
     * - the item is inserted and bubbled up accordingly
     * 
     * Return value: Returns the node where the word was inserted
     * 
     * Parameters:
     *  - word paragraph line are all data points about the word
     *  - currNode is the current root node (this will change during recursion)
     */

    public Node insert(Node currNode, String word, int paragraph, int line) {

        if (currNode == null) {
            return new Node(word, paragraph, line);
            // Create a new node for a key if there isn't a root already
        }

        if (currNode.left == null) {

            if (currNode.isTwoNode) {

                if (word.equals(currNode.firstWord)) {
                    // The word already exists, but we need to add the location

                    currNode.firstLocation.add(new Location(paragraph, line));
                    return currNode;
                }

                if (word.compareTo(currNode.firstWord) < 0) {
                    // If the word to be inserted is less than the root's first value

                    currNode.secondWord = currNode.firstWord;
                    currNode.secondLocation = currNode.firstLocation;
                    currNode.firstWord = word;
                    // Copy over the locations

                    currNode.firstLocation = new ArrayList<>();
                    currNode.firstLocation.add(new Location(paragraph, line));
                    // Simply add the word to left of the current word at the root, in the same node.
                    // This now becomes a three node
                }
                
                else {
                    currNode.secondWord = word;
                    // If the word inserted is greater than the root's first value, insert to the right of that value.
                    // This node now becomes a three node

                    currNode.secondLocation = new ArrayList<>();
                    currNode.secondLocation.add(new Location(paragraph, line));
                }

                currNode.isTwoNode = false;
                return currNode;
                // Successful add, set threeNode flag to true
            }

            // This part checks for duplicate words in three nodes

            if (word.equals(currNode.firstWord)) {
                // If the word = the first word in the node, add the location

                currNode.firstLocation.add(new Location(paragraph, line));
                return currNode;
            }

            if (word.equals(currNode.secondWord)) {
                // Add location of the second word

                currNode.secondLocation.add(new Location(paragraph, line));
                return currNode;
            }

            // If the node is a three node, we need to split it so we can add the word

            String[] words = {currNode.firstWord, currNode.secondWord, word};
            // I'm splitting the keys and the word into an array for easy access

            List<Location>[] locations = new List[3];

            locations[0] = currNode.firstLocation;
            locations[1] = currNode.secondLocation;
            locations[2] = new ArrayList<>();
            locations[2].add(new Location(paragraph, line));
            // Keep the locations consistant with the words

            // I decided to go for a bubble sort here, since the original iteration of the program
            // Used java's collections.sort
            // This sort keeps the locations of the words consistent when splitting
            for (int i = 0; i < 2; i++) {

                for (int j = i + 1; j < 3; j++) {

                    if (words[i].compareTo(words[j]) > 0) {

                        String tempWord = words[i];
                        words[i] = words[j];
                        words[j] = tempWord;
                        // Switch words

                        List<Location> tempLocations = locations[i];

                        locations[i] = locations[j];
                        locations[j] = tempLocations;
                        // Switch locations as well
                    }
                }
            }

            Node left = new Node(words[0]);
            Node right = new Node(words[2]);
            // Left and right nodes will be the "extremes" - that is the smallest and biggest value

            left.firstLocation = locations[0];
            right.firstLocation = locations[2];


            Node theParent = new Node(words[1]);
            // Middle value will be the parent

            theParent.firstLocation = locations[1];

            theParent.left = left;
            theParent.right = right;
            // Set the left and right child

            return theParent;
        }

        Node result;

        if (currNode.isTwoNode) {

            if (word.equals(currNode.firstWord)) {
                // Word already found, we just add the location
                currNode.firstLocation.add(new Location(paragraph, line));
                return currNode;
            }

            if (word.compareTo(currNode.firstWord) < 0) {
                // If the word is less than the first value of the current node

                result = insert(currNode.left, word, paragraph, line);
                // Recurse on the left subtree since we know it needs to be inserted somewhere in there

                if (result == currNode.left) {
                    // The word was successfully inserted, we can return
                    return currNode;
                }

                return bubbleUpTwoLeftSide(currNode, result);
                // Handle the split bubbling upward into this two node
            } 
            
            else {
                // If the word is greater than or equal to the first value of the current node

                result = insert(currNode.right, word, paragraph, line);
                // Recurse on the right subtree since we know it needs to be inserted somewhere in there

                if (result == currNode.right) {
                    // Word was successfully inserted so we can return
                    return currNode;
                }

                return bubbleUpTwoRightSide(currNode, result);
                // Handle split bubbling upward into the two node
            }
        } 
        
        else {
            // The current node is a three node

            if (word.equals(currNode.firstWord)) {
                // Already found the word in the first node, add location to it
                currNode.firstLocation.add(new Location(paragraph, line));
                return currNode;
            }
            if (word.equals(currNode.secondWord)) {
                // Already found the word in the second node, add location to it
                currNode.secondLocation.add(new Location(paragraph, line));
                return currNode;
            }

            if (word.compareTo(currNode.firstWord) < 0) {
                // If the word is alphabetically less than the first key of the current root node

                result = insert(currNode.left, word, paragraph, line);
                // Recurse on the left subtree, we know to insert in there

                if (result == currNode.left) {
                    // If the item was inserted correctly, return it
                    return currNode;
                }

                return bubbleUpThreeLeftSide(currNode, result);
                // Handle split bubbling upward into the three node

            } else if (word.compareTo(currNode.secondWord) < 0) {
                // If word is alphabetically less than the second key of the root node

                result = insert(currNode.middle, word, paragraph, line);
                // First recursively insert the word

                if (result == currNode.middle) {
                    // If node was inserted, return
                    return currNode;
                }

                return bubbleUpThreeMiddleSide(currNode, result);
                // Handle split bubbling upward into the three node

            } else {
                // Word must be alphabetically greater than the second key of the node

                result = insert(currNode.right, word, paragraph, line);
                // FIrst recursively insert the word

                if (result == currNode.right) {
                    // If the node was inserted correctly, return
                    return currNode;
                }

                return bubbleUpThreeRightSide(currNode, result);
                // Handle split bubbling upward into the three node
            }
        }
    }

    /*
     * Method: bubbleUpTwoLeftSide
     *
     * Purpose: This function inserts the words
     * from a split child on the left side into a two node parent, 
     * which will convert it to a three node (why we have the flag there).
     * It shifts the parent's existing words and pointers so the overall tree
     * is a valid 2 - 3 tree.
     * 
     * Pre-condition:
     *  - Parent is a two node, split is the result of splitting the parent's
     * left child. split contains the promoted key 
     * 
     * Post-condition: 
     * - parent becomes a three node, and its keys are pointers are updated to
     * cater towards a three node structure
     * 
     * Return value: Returns the updated parent node
     * 
     * Parameters:
     *  - parent is the 2 node recieving the promoted key
     *  - split is the node which contains the promoted key and the two subtrees
    */

    private Node bubbleUpTwoLeftSide(Node parent, Node split) {

        parent.secondWord = parent.firstWord;
        parent.secondLocation = parent.firstLocation;
        parent.firstWord = split.firstWord;
        parent.firstLocation = split.firstLocation;
        // Shift the parent first and second key and insert the split word into the first word, keeping locations

        Node oldRight = parent.right;

        parent.left = split.left;
        parent.middle = split.right;
        parent.right = oldRight;
        // Reassign the parent's child pointers so parent's children creates a three node

        parent.isTwoNode = false;
        // This split leaves the node to be a three node, so adjust the flag ( the parent now has 2 words)

        return parent;
    }

    /*
     * Method: bubbleUpTwoRightSide
     *
     * Purpose: This function inserts the words
     * from a split child on the right side into a two node parent, 
     * which will convert it to a three node (why we have the flag there).
     * It shifts the parent's existing words and pointers so the overall tree
     * is a valid 2 - 3 tree.
     * 
     * Pre-condition:
     *  - Parent is a two node, split is the result of splitting the parent's
     * right child. split contains the promoted key 
     * 
     * Post-condition: 
     * - parent becomes a three node, and its keys are pointers are updated to
     * cater towards a three node structure
     * 
     * Return value: Returns the updated parent node
     * 
     * Parameters:
     *  - parent is the 2 node recieving the promoted key
     *  - split is the node which contains the promoted key and the two subtrees
    */

    private Node bubbleUpTwoRightSide(Node parent, Node split) {

        parent.secondWord = split.firstWord;
        parent.secondLocation = split.firstLocation;
        // Adjust the second key to be the split key with its locations as well

        parent.middle = split.left;
        parent.right = split.right;
        // Move around the middle and right parent nodes to account for the split node

        parent.isTwoNode = false;
        // Leaves the parent as a three node, adjust flag accordingly
        return parent;
    }

    /*
     * Method: bubbleUpThreeLeftSide
     *
     * Purpose: This function handles the case where a three node parent gets a promoted key
     * from a split of its left child. The function combines the promoted key with the parent's existing two keys,
     * and sorts all three of them, then constructs two child nodes and a new parent node so that the tree
     * can remain a valid two three tree.
     * 
     * Pre-condition:
     *  - parent is a three-node, and split is the result of splitting the parent's left child and
     * contains the promoted key and its left and right subtrees
     * 
     * Post-condition: 
     * - We create a new parent node, the keys from the split and the original are sorted. We create
     * two new chold nodes which are made from the smallest and largest values from the three words,
     * using bubble sort to keep the locations consistant. The middle key then becomes the parent
     * 
     * Return value: Returns the parent node after reassigning and updating values
     * 
     * Parameters:
     *  - parent is the 3 node recieving the promoted key
     *  - split is the node which contains the promoted key and the two subtrees
    */

    private Node bubbleUpThreeLeftSide(Node parent, Node split) {

        String[] words = {split.firstWord, parent.firstWord, parent.secondWord};
        // I'm putting in the data into an array for clarity and ease of sortedness
        // This just saves me from doing a bunch of if statements to sort

        List<Location>[] locations = new List[3];
        locations[0] = split.firstLocation;
        locations[1] = parent.firstLocation;
        locations[2] = parent.secondLocation;
        // Keeping the locations specific to the words


        // JUst like last method, use bubble sort to keep the locations consistent with their words when sorting
        for (int i = 0; i < 2; i++) {

            for (int j = i + 1; j < 3; j++) {

                if (words[i].compareTo(words[j]) > 0) {

                    String tempWord = words[i];
                    words[i] = words[j];
                    words[j] = tempWord;
                    // Switch words

                    List<Location> tempLocation = locations[i];
                    locations[i] = locations[j];
                    locations[j] = tempLocation;
                    // Switch locations
                }
            }
        }

        Node left = new Node(words[0]);
        left.firstLocation = locations[0];
        // Left location

        Node right = new Node(words[2]);
        right.firstLocation = locations[2];
        // Rright location

        Node newParent = new Node(words[1]);
        newParent.firstLocation = locations[1];
        // The left and right nodes are the "extremes" of the combonation of first second  words and the current node

        left.left = split.left;
        left.right = split.right;
        // Adjust the left and right subtrees of the left node to reflect the bubble up left

        right.left = parent.middle;
        right.right = parent.right;
        // Same with the right subtree

        newParent.left = left;
        newParent.right = right;
        // Fix the new parent accordingly

        return newParent;
    }

    /*
     * Method: bubbleUpThreeMiddleSide
     *
     * Purpose: This function handles the case where a three node parent gets a promoted key
     * from a split of its middle child. The function combines the promoted key with the parent's existing two keys,
     * and sorts all three of them, then constructs two child nodes and a new parent node so that the tree
     * can remain a valid two three tree.
     * 
     * Pre-condition:
     *  - parent is a three-node, and split is the result of splitting the parent's middle child and
     * contains the promoted key and its left and right subtrees
     * 
     * Post-condition: 
     * - We create a new parent node, the keys from the split and the original are sorted. We create
     * two new chold nodes which are made from the smallest and largest values from the three words,
     * using bubble sort to keep the locations consistant. The middle key then becomes the parent
     * 
     * Return value: Returns the parent node after reassigning and updating values
     * 
     * Parameters:
     *  - parent is the 3 node recieving the promoted key
     *  - split is the node which contains the promoted key and the two subtrees
    */

    private Node bubbleUpThreeMiddleSide(Node parent, Node split) {
        // This function is for bubbling up on the middle node of a three node

        String[] words = {parent.firstWord, split.firstWord, parent.secondWord};
        // Also putting the data into an array for ease of sorting

        List<Location>[] locations = new List[3];
        locations[0] = parent.firstLocation;
        locations[1] = split.firstLocation;
        locations[2] = parent.secondLocation;

        // Same as last two functions, bubble sort to keep the locations with their words when sorting
        for (int i = 0; i < 2; i++) {

            for (int j = i + 1; j < 3; j++) {

                if (words[i].compareTo(words[j]) > 0) {

                    String tempWord = words[i];
                    words[i] = words[j];
                    words[j] = tempWord;
                    // Switch the words

                    List<Location> tempLocation = locations[i];
                    locations[i] = locations[j];
                    locations[j] = tempLocation;
                    // Switch the locations
                }
            }
        }

        Node left = new Node(words[0]);
        left.firstLocation = locations[0];
        // Left side

        Node right = new Node(words[2]);
        right.firstLocation = locations[2];
        // Right side

        Node newParent = new Node(words[1]);
        newParent.firstLocation = locations[1];
        // Data extremes are the left and right sides of the array

        left.left = parent.left;
        left.right = split.left;
        // Adjusting the left subtree to fit the new split node

        right.left = split.right;
        right.right = parent.right;
        // Adjusting the right tree to fit the new split node

        newParent.left = left;
        newParent.right = right;
        // Adjusting the root's left and right subtrees

        return newParent;
    }

    /*
     * Method: bubbleUpThreeRightSide
     *
     * Purpose: This function handles the case where a three node parent gets a promoted key
     * from a split of its right child. The function combines the promoted key with the parent's existing two keys,
     * and sorts all three of them, then constructs two child nodes and a new parent node so that the tree
     * can remain a valid two three tree.
     * 
     * Pre-condition:
     *  - parent is a three-node, and split is the result of splitting the parent's right child and
     * contains the promoted key and its left and right subtrees
     * 
     * Post-condition: 
     * - We create a new parent node, the keys from the split and the original are sorted. We create
     * two new chold nodes which are made from the smallest and largest values from the three words,
     * using bubble sort to keep the locations consistant. The middle key then becomes the parent
     * 
     * Return value: Returns the parent node after reassigning and updating values
     * 
     * Parameters:
     *  - parent is the 3 node recieving the promoted key
     *  - split is the node which contains the promoted key and the two subtrees
    */

    private Node bubbleUpThreeRightSide(Node parent, Node split) {

        String[] words = {parent.firstWord, parent.secondWord, split.firstWord};
        // Putting the data in an array for ease of sorting

        List<Location>[] locations = new List[3];
        locations[0] = parent.firstLocation;
        locations[1] = parent.secondLocation;
        locations[2] = split.firstLocation;
        // Keep track of the locations in those words

        // Just like the other functions, bubble sort for keeping track of the locations
        for (int i = 0; i < 2; i++) {

            for (int j = i + 1; j < 3; j++) {

                if (words[i].compareTo(words[j]) > 0) {

                    String tempW = words[i];
                    words[i] = words[j];
                    words[j] = tempW;
                    // Switch words

                    List<Location> tempL = locations[i];
                    locations[i] = locations[j];
                    locations[j] = tempL;
                    // Switch locations
                }
            }
        }

        Node left = new Node(words[0]);
        left.firstLocation = locations[0];
        // Left node

        Node right = new Node(words[2]);
        right.firstLocation = locations[2];
        // RIght node

        Node newParent = new Node(words[1]);
        newParent.firstLocation = locations[1];
        // Left and right of the array are

        left.left = parent.left;
        left.right = parent.middle;
        // Adjust the left subtree to fit in the split node

        right.left = split.left;
        right.right = split.right;
        // Adjust the right subtree to fit in the split node

        newParent.left = left;
        newParent.right = right;
        // Adjust the parent node with the left and right children

        return newParent;
    }

    /*
     * Method: search (v1)
     * Purpose: calls the helper recursive class for finding a word in the tree
     * 
     * Pre-condition:
     *  - The word isn't found yet and the tree has not been traversed
     * 
     * Post-condition: 
     * - The word is found or isnt found after traversal
     * 
     * Return value: Returns a list of the locations or null if the
     * word is found or not
     * 
     * Parameters:
     *  word is a string word to be found in the tree
     */

    public List<Location> search(String word) {
        // Search function for finding a key, I'm just calling an external function with the root node
        return search(this.root, word);
    }

    /*
     * Method: search(v2)
     * Purpose: Recursivly calls search on left middle or right subtrees
     * in an efort to find the word in the tree, it does so by comparing
     * the values and recursing if they are greater than, less than, etc
     * than the input word, since a 2-3 tree is also a BST.
     * 
     * Pre-condition:
     *  - The node isn't found
     * 
     * Post-condition: 
     * - The node has or has not been found
     * 
     * Return value: Returns a list of locations associated with that word if it is
     * found, or null if it isnt found.
     * 
     * Parameters:
     *  - node is the current node being evaluated (root node of the current
     * recursion tree), and word is the word to be found
     */

    public List<Location> search(Node node, String word) {

        if (node == null) {
            // If there isn't a tree at all, the value surely isn't in it
            return null;
        }

        if (node.isTwoNode) {
            // Case: the node is a two node

            if (word.equals(node.firstWord)) {
                return node.firstLocation;
                // We found the word
            }

            if (word.compareTo(node.firstWord) < 0) {
                // IF word is less than the first word in the node, we go left
                return search(node.left, word);
            } 
            else {
                // Otherwise we go right
                return search(node.right, word);
            }
        } 

        else {
            // Must be a three node

            if (word.equals(node.firstWord)) {
                // If the word = the first word, we return its location
                return node.firstLocation;
            }
            if (word.equals(node.secondWord)) {
                // If the word = the second word, we return its location
                return node.secondLocation;
            }

            if (word.compareTo(node.firstWord) < 0) {
                // Word is less than the first word in the node, recurse left
                return search(node.left, word);

            } 
            else if (word.compareTo(node.secondWord) < 0) {
                // Less than the second word, greater than the first word, recurse middle
                return search(node.middle, word);
            } 
            else {
                // Otherwide word is greater than the right word, recurse right
                return search(node.right, word);
            }
        }
    }

    /*
     * Method: getWords(v1)
     * Purpose: calls the helper recusive function to to add the list of
     * words in the tree.
     * 
     * Pre-condition:
     *  - The getwords list is empty and no traversal has been done yet.
     * 
     * Post-condition: 
     * - We have a list of all the words in the tree in alphabetical order
     * 
     * Return value: Returns a list of words which were in the text in alphabetical order
     * 
     * Parameters:
     *  - none
     */

    public List<String> getWords() {

        List<String> result = new ArrayList<>();

        getWords(this.root, result);

        return result;
    }

    /*
     * Method: getWords(v2)
     * Purpose: Handles the recursion and actual logic for getting
     * all of the words in alphabetical order. Since a 2 3 tree is also
     * a BST, we can treat the recursion normally.
     * 
     * Pre-condition:
     *  - Haven't found the nodes alphabetically
     * 
     * Post-condition: 
     * - Each node has been visited, and added to the list
     * alphabetically
     * 
     * Return value: Returns nothing, but modifies the input list
     * 
     * Parameters:
     *  - node is the current root node of the recursion tree
     *  - result is a list of the words which will be sorted in
     *  alphabetical order
     */

    private void getWords(Node node, List<String> result) {

        if (node == null) {
            // End recursion, we are past a leaf node
            return;
        }

        if (node.isTwoNode) {
            // We are at a two node, so recurse on the left and right child
            getWords(node.left, result);

            result.add(node.firstWord);

            getWords(node.right, result);

        } else {

            // We are at a three node, so recurse on the left, middle, right child

            getWords(node.left, result);
            result.add(node.firstWord);

            getWords(node.middle, result);
            result.add(node.secondWord);

            getWords(node.right, result);
        }
    }

    /*
     * Method: PrntwordOcurrences()
     * Purpose: Handles the formatting for printing out the words and
     * their locations in alphabetical order. It formats the printing
     * of words to be formmated correctly.
     * 
     * Pre-condition:
     *  - We have a list of words from the previous function, which is sorted
     * in alphabetical order. The function takes that list and formats it correctly
     * so it gets printed with correct spacing
     * 
     * Post-condition: 
     * - The list gets printed out, with the word followed by the occurrances,
     * with 8 to a line as stated in the spec
     * 
     * Return value: Returns nothing, prints all of the items
     * 
     * Parameters:
     *  No parameters, but calls the other function for grabbing the list
     * of alphabetized words
     */

    public void printWordOccurrences() {

        List<String> words = getWords();

        for (String word : words) {

            List<Location> locations = search(word);

            if (locations == null) {
                continue;
            }

            int count = 0;

            // First line prints the word
            System.out.printf("%-20s", word);

            for (int i = 0; i < locations.size(); i++) {

                if (count == 8) {
                    // Start a new line with a " mark if we have more than 8 items

                    System.out.println();
                    // Want to put the other values on a new line

                    System.out.printf("%-20s", "\"");
                    // I found this kind of formatting for whitespace from stack overflow

                    count = 0;
                }

                Location curr = locations.get(i);

                System.out.print("(" + curr.paragraph + "," + curr.line + ") ");
                // Format the locations
                count++;
            }

            System.out.println();
        }
    }
}