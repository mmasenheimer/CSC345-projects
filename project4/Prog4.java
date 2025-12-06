/*
 * Michael Masenheimer CSC 345 
 * Program #4: Concordance Construction with 2-3 Trees
 * Dr. McCann, Dr. Lynam, Jesse Chen, Moyeen Uddin, due November 25th
 * 
 * Description:
 *
 * This program is meant to give me practice with an entirely new
 * data structure, 2-3 trees. The program takes in a file of text (for example,
 * a .dat file). It parses the file to extract words, also keeping track of
 * location through paragraphs and lines where the word it. The program saves that
 * information and adds it to a 2-3 tree through an insertion method. The tree
 * has a few helper functions which help with bubbling up and growth at the root.
 * After an in-order traversal, the program prints out a formatted version of all words
 * and their locations in alphabetical order.
 * 
 * Operations:
 * 
 * This program uses Java version 24 or earlier, and the input is in the form of a file
 * through the command line (argument 0). There are two files, one to house the main driver file,
 * (This one), and one to house the 2-3 data structure, they must be in the same directory for
 * the program to work properly.
 * 
 * java Prog4 [filename]
 * 
 * As far as I know, all possible reasonable file inputs such as the empty file,
 * invalid file paths, and there currently is no issue
 * with the file parsing to the best of my knowledge.
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * Class: Prog4.java
 * Name: Michael Masenheimer
 * 
 * Package: Project4 (No inheritence), 
 * relies on TwoThree.java for the 2 3 data structure
 * 
 * Purpose: This class houses all of the logic for the entire program, it
 * consists of a singular main method which is responsible for parsing the file
 * and getting the words ready to be inserted into the tree
 * 
 * There are no class constants or variables as main is a static method
 * 
 * There are no constructers for this class, but main is called when the program
 * is executed from the command lines.
 */

public class Prog4 {
    public static void main(String[] args) {

        if (args.length == 0) {
            // Handle non-entered file edge case 
            System.out.println("Error: no input file provided");
            return;
        }

        try {
            File myFile = new File(args[0]);
            twoThree wordTree;
            try (Scanner myScanner = new Scanner(myFile)
            // Grab the input file and set the scanner to scan that file

            ) {
                int paragraphNumber = 0;
                int lineNumberInParagraph = 0;
                boolean newParagraph = true;
                // Counters and flags for keeping track of paragraph and line number

                wordTree = new twoThree();
                // The tree will hold all of the words and their associated locations

                while (myScanner.hasNextLine()) {

                    String currLine = myScanner.nextLine();
                    // Grab current line
                    
                    if (currLine.isEmpty()) {
                        // This must mean we are starting a new paragraph
                        newParagraph = true;
                        continue;
                    }
                    
                    if (newParagraph == true) {
                        
                        paragraphNumber += 1;
                        // Add to paragraph counter
                        
                        lineNumberInParagraph = 1;
                        
                        newParagraph = false;
                        // Not in a new paragraph
                    }
                    
                    else {
                        lineNumberInParagraph += 1;
                        // We still must add to the lines in the paragraph
                    }
                    
                    String[] lineContent = currLine.split("\\s+");
                    // We can split on whitespace (I found "\\s+" from stack overflow)
                    
                    for (String word : lineContent) {
                        // Loop through each word to check characters
                        
                        int i = 0;
                        int n = word.length();
                        // i is the current character n is the total len of th  word
                        
                        while (i < n) {
                            
                            if (!Character.isLetterOrDigit(word.charAt(i))) {
                                // Skip leading characters that aren't letters or digits
                                i += 1;
                                continue;
                            }
                            
                            StringBuilder sb = new StringBuilder();
                            // Accumulation of valid characters
                            
                            while (i < n) {
                                char c = word.charAt(i);
                                if (Character.isLetterOrDigit(c) || c == '-' || c == '\'') {
                                    // Include letters digits hyphens and apostrophes in the word
                                    sb.append(c);
                                    i++;
                                }
                                else {
                                    // Stop if we hit a char that isn't part of the word
                                    break;
                                }
                            }
                            
                            String currWord = sb.toString().toLowerCase();
                            // Convert to lowercase according to the spec
                            
                            if (!currWord.isEmpty()) {
                                // Only add non empty words into the tree
                                wordTree.insert(currWord, paragraphNumber, lineNumberInParagraph);
                            }
                            
                        }
                    }
                }
            }

            System.out.println("Word                Occurrences [form: (Paragraph#, Line#)]");
            System.out.println("----                -----------");
            // Output header for printing the words out

            wordTree.printWordOccurrences();
            // Print the nodes and their associated data out

        } catch (FileNotFoundException e) {

            System.out.println("Error: file was not found");
        }
    }
}
