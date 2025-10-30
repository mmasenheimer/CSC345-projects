/*
 * Michael Masenheimer CSC 345 
 * Program #3: Sorted mbox
 * Dr. McCann, Dr. Lynam, Jesse Chen, Moyeen Uddin, due October 30th
 * 
 * Description:
 *
 * This program is meant to give me both practice with java file i/o,
 * line parsing, and sorting algorithms that we have studied in class.
 * The program reads an mbox file containing email information
 * and creates objects for the messages, creating 2 files- one which sorts
 * the messages using Java's Collections.Sort (timsort) and an output file
 * which contains the messages sorted with my own sorts (mergesort and modified quicksort).
 * The program times how long it takes to sort such messages. 
 * In terms of data structures, this program uses ArrayLists to store message objects,
 * and for ease of sorting.
 * 
 * Why I chose modified quicksort for date and mergesort for sender sort:
 * 
 * Modified quicksort: I chose quicksort with an insertionsort threshold (12) for the
 * data sort since it is a good O(n log n) algorithm for 
 * roughly random (depending on the dataset) sorted mbox messages. It's 
 * also a decent algorithm for large datasets, since we are working with files with hundreds of 
 * thousands to millions of lines, meaning a lot of message objects. I also found it
 * was easy to work with the compareTo method from the Comparable implementation for Message class.
 * The threshold of 12 allows it to switch to insertion sort when the list gets to 12 elements,
 * which improves on time sice insertionsort is faster with smaller datasets than quicksort.
 * 
 * MergeSort: I chose mergeSort becuase of the possibility of identical senders, in which it
 * handles well. For senders like "alice" and "alicia", we are not losing performance on these
 * similarly-named senders. MergeSort also perserves the original order of equal elements, which
 * I assumed would be important. I tried shellsort for sort by sender, which didn't do too bad,
 * but I noticed it was falling behind timsort for large datasets, by around 0.05-0.1 seconds.
 * 
 * Operations:
 * 
 * This program uses Java version 24 or earlier, and the input is in the form of a file path
 * followed by the type of sort to be performed, which could be by date or sender.
 * You can run it by inputting the following into the terminal:
 * 
 * java Prog3 mbox-orig date
 * 
 * As far as I know, all possible reasonable file inputs such as the empty file,
 * invalid file paths, io errors are all handled, and there currently is no issue
 * with the file parsing to the best of my knowledge.
 */

 /*
 * Class: Prog3.java
 * Name: Michael Masenheimer
 * 
 * Package: Project3 (No inheritence), 
 * relies on no other files, but still needs java io and util imports
 * 
 * Purpose: This class houses all of the logic for the entire program, it
 * consists of a few static methods and nested classes (also commented for their
 * functionality). Main handles the organization of the logic.
 * 
 * As stated before, there are no external classes this class relies on, and there
 * are no class constants or fields, only static methods within this class.
 * 
 * There are no constructers for this class, but main is called when the program
 * is executed from the command lines.
 */

package project3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Prog3 {

    /*
     * Class: dateTime
     * Name: Michael Masenheimer
     * 
     * Package: Project3 (No inheritence), 
     * relies on no other files
     * 
     * Purpose: This class houses is meant to be implemented as one of the fields
     * in the Message class, for data organization and clarity. This file holds the
     * year, month, day, hour, minute, and second of a given message.
     * 
     * As stated before, there are no external classes this class relies on, and there
     * are no class constants or fields, only static methods within this class.
     * 
     * Public class variables- Self explanatory (each are ints representing a certain
     * time increment from the message)
     * 
     * dateTime(year, month, day, hour, minute, second) - This constructor takes in
     * 6 integers and sets their respective time periods
    */

    public static class dateTime {

    public int year;
    public int month;
    public int day;
    public int hour;
    public int minute;
    public int second;

    /*
     * Method: dateTime
     * Purpose: Initializes the fields for dateTime class
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
     *  - Self-explanatory (year, month, day, hour, minute, second) representing
     *  the time stamp of when the message was sent
     */

    public dateTime(int year, int month, int day, int hour, int minute, int second) {
        // Set the fields for each timestamp
       this.year = year;
       this.month = month;
       this.day = day;
       this.hour = hour;
       this.minute = minute;
       this.second = second;
    }

    /*
     * Class: Message
     * Name: Michael Masenheimer
     * 
     * Package: Project3 (No inheritence), 
     * relies on no other files
     * 
     * Purpose: This class represents a "message" object-that is a parsed message
     * converted to an object, where fields are set to show information about that
     * object. The sorting implemtation below will sort these objects in an ArrayList.
     * 
     * This class needs to have a dateTime object as the time attribute, for sorting. It uses
     * the Comparable class as well. So it relies on 2 other classes. The comparable
     * is for comparing two Message objects by date, used in a date sort.
     * 
     * sender-String representing the name of the sender of the message
     * time- dateTime object with attributes min, sec, day, year, hour, etc.
     * content- The full content of the email, including everything except for the header
     * fullHeader- String representing the complete header of the message.
     * 
     * It implements the Comparable<Message> interface, allowing
     * Message objects to be compared and sorted based on a chosen field
     * (such as date, sender, or subject).
     * 
     * Message(String header, Strign content)- This constructor parses an input header and
     * sets all of the required fields for the class, using a switch statement to determine
     * the month, since mbox headers use months in abc form.
    */

}
    public static class Message implements Comparable<Message> {

        public String sender;
        public dateTime time;
        public String content;
        public String fullHeader;

        /*
         * Method: Message
         * Purpose: Initializes fields for the Message class using switch statements to
         * detemrine months, and by further parsing the input message string.
         * 
         * Pre-condition:
         *  - A message has not yet been converted to a message object and the fields
         *  have not been instantiated yet.
         * 
         * Post-condition: The string is parsed, fields instantiated, and later on in
         * the code, added to an array for sorting.
         * 
         * Return value: Returns nothing
         * 
         * Parameters:
         *  - header is a String representing the header of the mbox message, in the form:
         *  From MAILER-DAEMON Thu Oct  4 10:23:46 2007
         * 
         *  - content is a string representation of the actual content of the message, which
         *  is found before creating the message object.
        */

        public Message(String header, String content) {

            String[] parts = header.split("\\s+");
            // Split by variable length spaces (I found out about \\s+ from a stack overflow post)

            this.sender = parts[1];
            this.content = content;
            this.fullHeader = header;

            // Parts for date/time (e.g., "Wed Oct 3 11:43:35 2007")
            String monthString = parts[3];

            int day = Integer.parseInt(parts[4]);

            // Now we extract the specific times in the message
            String[] dates = parts[5].split(":");

            int hour = Integer.parseInt(dates[0]);
            int minute = Integer.parseInt(dates[1]);
            int second = Integer.parseInt(dates[2]);
            int year = Integer.parseInt(parts[6]);

            int month = 1;
            switch (monthString) {
                // Extract which month in the header
                case "Jan" -> month = 1;
                case "Feb" -> month = 2;
                case "Mar" -> month = 3;
                case "Apr" -> month = 4;
                case "May" -> month = 5;
                case "Jun" -> month = 6;
                case "Jul" -> month = 7;
                case "Aug" -> month = 8;
                case "Sep" -> month = 9;
                case "Oct" -> month = 10;
                case "Nov" -> month = 11;
                case "Dec" -> month = 12;
            }

            this.time = new dateTime(year, month, day, hour, minute, second);
            // Record the dateTime object
        }

        /*
         * Method: Getters
         * Purpose: Extract information from the fields.
         * 
         * Return value: Returns respective data associated with the field
         *
        */

        public String getSender() {
            return sender;
        }

        public dateTime getTime() {
            return time;
        }

        public String getContent() {
            return content;
        }

        public String getHeader() {
            return fullHeader;
        }

        /*
         * Method: compareTo
         * Purpose: Overrides from the Comparable implementation,
         * used to compare two message objects by date and time through
         * a series of if and else statements
         * 
         * Pre-condition:
         *  - None
         * 
         * Post-condition: Another message is compared to the current one
         * based on year, month, day, etc.
         * 
         * Return value: Returns a negative int if the message is earlier than
         * other, 0 if they have the same date and time, and a positive value if
         * this message is later than the other one
         * 
         * Parameters:
         *  - other is another Message object for comparison
        */

        @Override
        public int compareTo(Message other) {
            // Compare by the year first, then month, etc.
            if (this.time.year != other.time.year) {
                return Integer.compare(this.time.year, other.time.year);
            }
            if (this.time.month != other.time.month) {
                return Integer.compare(this.time.month, other.time.month);
            }
            if (this.time.day != other.time.day) {
                return Integer.compare(this.time.day, other.time.day);
            }
            if (this.time.hour != other.time.hour) {
                return Integer.compare(this.time.hour, other.time.hour);
            }
            if (this.time.minute != other.time.minute) {
                return Integer.compare(this.time.minute, other.time.minute);
            }
    
            return Integer.compare(this.time.second, other.time.second);
        }
    }

    /*
         * Method: parseInput
         * Purpose: This method takes in a file scanner and a sort type, and creates
         * the message objects from the file, making sure to save the content of the message
         * while iterating. Essentially, this function sets up everything (making an
         * ArrayList<Message> of objects to be sorted down the road), and it returns this List.
         * 
         * Pre-condition:
         *  - The file has not been iterated over and Message objects have not been made yet. The
         *  ArrayList has not been set up yet, so sorting cannot be completed.
         * 
         * Post-condition: The file is parsed, objects made and organized into sortable
         * format.
         * 
         * Return value: Returns the arrayList of Messages, which were created from the mbox data
         * 
         * Parameters:
         *  - fileScanner is a Scanner object which houses all of the contents of the input
         *  file.
        */

    public static ArrayList<Message> parseInput(Scanner fileScanner) {

        ArrayList<Message> theMessages = new ArrayList<>();

        StringBuilder contentBuilder = new StringBuilder();
        String headerLine = null;
        // Variables to handle the header and content for each message, when recognized

        while (fileScanner.hasNextLine()){

            String line = fileScanner.nextLine();
            // Grab the current line in the file

            if (line.startsWith("From ")) {
                // This must me a message

                if (headerLine != null) {
                    Message curr = new Message(headerLine, contentBuilder.toString());
                    theMessages.add(curr);
                    // Complete content recognized and message object created
                }

                headerLine = line;
                contentBuilder = new StringBuilder();
                // Continue building the content
            }
            else {

                if (contentBuilder.length() > 0) {
                    contentBuilder.append("\n");
                    // Preserving the line breaks within the message body
                }
                contentBuilder.append(line);
            }
        }

        if (headerLine != null) {
            Message curr = new Message(headerLine, contentBuilder.toString());
            theMessages.add(curr);
            // Finally, we want to add the last message in the file,
            // which must be done outside of the loop, since contentBuilder is finished
        }

        return theMessages;
    }

    /*
     * Method: mySortDate
     * Purpose: This method is the root method for my sorting implementation for
     * sort by date. The actual sorting algorithm is a modified Quicksort using
     * an insertion sort threshold, which was mentioned in class.
     * 
     * Pre-condition:
     *  - The array hasn't been sorted yet
     * 
     * Post-condition: The array is sorted after calling the proper sorting functions
     * 
     * Return value: Returns an ArrayList<Message> of the sorted messages by date
     * 
     * Parameters:
     *  - input is an ArrayList of messages, which has not been sorted yet
    */
    
    public static ArrayList<Message> mySortDate(ArrayList<Message> input) {

        quickSortByDate(input, 0, input.size() - 1, 12);
        // I found that 12 is a decent threshold for when to use insertionSort,
        // As for smaller values, insertion sort is actually quicker
        return input;
    }

    /*
     * Method: quickSortByDate
     * Purpose: This method is a modified quicksort algorithm to handle
     * sorting on an ArrayList of messages, and it looks at the date and
     * time from each message and compares.
     * 
     * Pre-condition:
     *  - The array hasn't been sorted yet
     * 
     * Post-condition: The array is sorted after recursing and calling
     * insertion sort.
     * 
     * Return value: Returns nothing, but sorts the array
     * 
     * Parameters:
     *  - input is an ArrayList of messages, which has not been sorted yet, low starts
     *  as 0, the index of the first element, high is the index of the last element, and
     *  insertionsortwhen is a threshold which tells the method when to activate insertionSort
     *  on the remaining input array
    */

    public static void quickSortByDate(ArrayList<Message> input, int low, int high, int insertionSortWhen) {

        if (high - low < insertionSortWhen) {
            insertionSort(input, low, high);
            // If the array is small enough, insertion sort is faster
            return;
        }

        if (low < high) {
            int mid = low + (high - low) / 2;
            Collections.swap(input, mid, high);
            // Find midpoint and move it to the end fo the array

            Message pivotPosition = input.get(high);
            int curr = low - 1;
            // Set the pivot to the element at the end

            for (int j = low; j < high; j++) {
                // Elements <= to the pivot message are moved left
                if (input.get(j).compareTo(pivotPosition) <= 0) {
                    curr++;
                    Collections.swap(input, curr, j);
                }
            }

            Collections.swap(input, curr+1, high);
            int pivotIndex = curr + 1;
            // Place the pivot element in the correct position

            quickSortByDate(input, low, pivotIndex - 1, insertionSortWhen);
            quickSortByDate(input, pivotIndex + 1, high, insertionSortWhen);
            // Sort the sublists before / after thepivot

        }
    }

    /*
     * Method: insertionSort
     * Purpose: This method is the classic insertionSort sorting method,
     * Sorting an array by inserting elements into their correct portion
     * of the list
     * 
     * Pre-condition:
     *  - The array hasn't been sorted yet
     * 
     * Post-condition: The array is sorted
     * 
     * Return value: Returns nothing, but sorts the array
     * 
     * Parameters:
     *  - input is an ArrayList of messages, which has not been sorted yet, low starts
     *  as 0, the index of the first element, high is the index of the last element.
    */

    private static void insertionSort(ArrayList<Message> input, int low, int high) {

        for (int i = low + 1; i <= high; i++) {

            Message key = input.get(i);
            int before = i - 1;
            // Store current message to be sorted

            while (before >= low && input.get(before).compareTo(key) > 0) {
                // Shift messages that are greater than the key to the right

                input.set(before + 1, input.get(before));
                before--;
            }

            input.set(before + 1, key);
            // Insert the message at the correct position
        }
    }

    /*
     * Method: mySortSender
     * Purpose: This method is the root method for the mergeSort
     * alogrithm, calling mergeSort with additional parameters based on
     * mySortSender's input array. The series of functions sorts the messages
     * by sender in alphabetical order
     * 
     * Pre-condition:
     *  - The array hasn't been sorted yet
     * 
     * Post-condition: The array is sorted after running mergeSort
     * 
     * Return value: Returns the array with all sorted messages
     * 
     * Parameters:
     *  - input is an ArrayList of messages, which has not been sorted yet
    */

    public static ArrayList<Message> mySortSender(ArrayList<Message> input) {
    
    mergeSort(input, 0, input.size() - 1);
    // Call mergeSort with left and right pointers

    return input;
    }

    /*
     * Method: mergeSort
     * Purpose: This method is responsible for sorting the input array,
     * and does so by recursively breaking down the array and sorting
     * the sub arrays by calling the other half of mergeSort, Merge. 
     * 
     * Pre-condition:
     *  - The array hasn't been sorted yet
     * 
     * Post-condition: The array is sorted after running mergeSort
     * 
     * Return value: Returns nothing, but sorts the content of the file.
     * 
     * Parameters:
     *  - input is an ArrayList of messages, which has not been sorted yet, left
     *  is a pointer to the index at the start of the array, right is a pointer
     *  to the index at the end of the array
    */

    private static void mergeSort(ArrayList<Message> input, int left, int right) {

        if (left < right) {
            int mid = left + (right - left) / 2;
            // Find the middle of the array
            
            mergeSort(input, left, mid);
            mergeSort(input, mid + 1, right);
            // Sort first and second halves
        
            merge(input, left, mid, right);
            // Merge the sorted halves
        }
    }

    /*
     * Method: merge
     * Purpose: This method is responsible for merging and sorting
     * an input ArrayList. It zips up the left and right side of
     * the input array.
     * 
     * Pre-condition:
     *  - The array hasn't been sorted yet
     * 
     * Post-condition: The array is sorted after running mergeSort
     * 
     * Return value: Returns nothing, but merges the contents of the array
     * 
     * Parameters:
     *  - input is an ArrayList of messages, which has not been sorted yet, left
     *  is a pointer to the index at the start of the array, right is a pointer
     *  to the index at the end of the array, mid is a pointer to the middle
     *  element of the array.
    */

    private static void merge(ArrayList<Message> input, int left, int mid, int right) {
       
        int newOne = mid - left + 1;
        int newTwo = right - mid;
        // Temporary arrays for comparison
        
        ArrayList<Message> leftArray = new ArrayList<>(newOne);
        ArrayList<Message> rightArray = new ArrayList<>(newTwo);
       
        for (int i = 0; i < newOne; i++) {
            // Copy content to left array

            leftArray.add(input.get(left + i));
        }

        for (int j = 0; j < newTwo; j++) {
            // Copy content to right array

            rightArray.add(input.get(mid + 1 + j));
        }
        
        int i = 0;
        int j = 0;
        int k = left;
        // Merge the arrays back
        
        while (i < newOne && j < newTwo) {
            // Make sure there are elements remaining

            if (leftArray.get(i).getSender().compareTo(rightArray.get(j).getSender()) <= 0) {
                // Compare sender names from left and right sub arrays
                input.set(k, leftArray.get(i));
                i++;
                // Left array incr
            } 
            else {
                input.set(k, rightArray.get(j));
                j++;
                // Right array incr
            }
            k++;
        }
        
        // Copy remaining elements from either sub array if one has been used up
        while (i < newOne) {
            input.set(k, leftArray.get(i));
            i++;
            k++;
        }
        
        while (j < newTwo) {
            input.set(k, rightArray.get(j));
            j++;
            k++;
        }
    }

    /*
     * Method: startTiming
     * Purpose: This method is responsible for starting an internal
     * program clock for timing the sorting algorithms on the input data.
     * NOTE: This function was taken from the algorithm demo on the website.
     * 
     * Return value: Returns time at the start of the function call
     * 
     * Parameters: None
    */

    public static long startTiming () {
        // Taken from the 345 website
        System.gc();
        // Collect garbage
        return System.nanoTime();
        // Start timing
    }

     /*
     * Method: stopTiming
     * Purpose: This method is responsible for stopping the internal
     * program clock for timing the sorting algorithms on the input data
     * NOTE: This function was taken from the algorithm demo on the website.
     * 
     * Return value: Returns a double of the total elapsed time
     * 
     * Parameters: startingTime is the starting time of the internal clock
    */

    public static double stopTiming (long startingTime) {
        // Taken from the 345 website

        long elapsedTime = System.nanoTime() - startingTime;
        return elapsedTime / 1_000_000_000.0;
        // Return the time in seconds
    }

    /*
     * Method: main
     * Purpose: This method is responsible for handling all of the top-level
     * logic in the program. It takes in the file, sends it off to parse, and
     * calls the necessary sorting algorithms to sort the data, time the sorts,
     * and write the sorted messages to two output files.
     * 
     * Pre-condition:
     *  - The program has not been called yet. The precondition is that
     *  this function is provided with the necessary command line arguments
     *  to run the program
     * 
     * Post-condition: Two output files are created and written to, with the
     * proper sorted messages and 
     * 
     * Return value: Creates the files with the correct output
     * 
     * Parameters:
     *  - Command line arguments including the file path and the sort type
    */
    public static void main(String[] args) {

        int minutesJ = 0;
        long startTimeJ = 0;
        double secondsJ = 0.0;
        double durationJ = 0;
        // Timing stats for the java collection sort

        int minutesM = 0;
        long startTimeM = 0;
        double secondsM = 0.0;
        double durationM = 0;
        // Timing stats for my sort

        if (args.length != 2) {
            // If there are an invalid number of arguments
            System.out.println("Error: must have 2 arguments in the format format: java MboxSorter <filename> <sortType>");
            return;
        }

        String filePath = args[0];
        String sortType = args[1].toLowerCase();
        // Grab inputs

        if (!"date".equals(sortType) && !"sender".equals(sortType)) {
            // If the sort arguments do not match the spec

            System.out.println("Error: sort type must be 'date' or 'sender'");
            return;
        }

        try {
            // Try to read in the file
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            
            if (!scanner.hasNextLine()) {
                // Handle empty file case
                System.out.println("Error: the file is empty");
                return;
            }

            ArrayList<Message> javaResult = parseInput(scanner);
            ArrayList<Message> myResult = new ArrayList<>(javaResult);
            // Unsorted arrays to be sorted by the algorithms

            if (sortType.equals("date")) {
                startTimeJ = startTiming();
                // Sort by date

                // JAVA'S ALGORITHM

                Collections.sort(javaResult);

                durationJ = stopTiming(startTimeJ);

                minutesJ = (int)durationJ / 60;
                secondsJ = (int)durationJ % 60 + (durationJ - (int)durationJ);
                // Grab total seconds and minutes

                // MY SORTING ALGORITHM FOR DATE

                startTimeM = startTiming();
                // Sort by date

                mySortDate(myResult);

                durationM = stopTiming(startTimeM);

                minutesM = (int)durationM / 60;
                secondsM = (int)durationM % 60 + (durationM - (int)durationM);
                // Grab total seconds and minutes

            }

            else if (sortType.equals("sender")) {
                startTimeJ = startTiming();
                // Sort by sender

                // JAVA'S SORT

                Collections.sort(javaResult, (Message m1, Message m2) -> m1.getSender().compareTo(m2.getSender()));

                durationJ = stopTiming(startTimeJ);

                minutesJ = (int)durationJ / 60;
                secondsJ = (int)durationJ % 60 + (durationJ - (int)durationJ);
                // Grab seconds and minutes for timing

                // MY SORTING ALGORITHM FOR SENDER

                startTimeM = startTiming();
                // Sort by date

                mySortSender(myResult);

                durationM = stopTiming(startTimeM);

                minutesM = (int)durationM / 60;
                secondsM = (int)durationM % 60 + (durationM - (int)durationM);
                // Grab seconds and minutes for timing

            }

            try {
                // Try to write the files
                PrintWriter writerJava = new PrintWriter(new FileWriter(filePath + "-java"));
                PrintWriter writerMine = new PrintWriter(new FileWriter(filePath + "-mine"));

                for (int i = 0; i < javaResult.size(); i++) {
                    // Writing each message into its respective file

                    Message msgJ = javaResult.get(i);
                    Message msgM = myResult.get(i);

                    writerJava.println(msgJ.getHeader());
                    writerJava.print(msgJ.getContent());

                    writerMine.println(msgM.getHeader());
                    writerMine.print(msgM.getContent());
    
                    writerJava.println();
                    writerMine.println();
                    // Print a blank line after each message
        
                }

                writerMine.close();
                writerJava.close();

                System.out.println("""
                                   Sorting time for Java collections.sort:
                                   Minutes: """ + minutesJ + "\n" + "Seconds: " + secondsJ + "\n");
                
                if (sortType.equals("date")) {
                    // Print special line for quicksort
                    System.out.println("""
                                   Sorting time for quicksort/insertion sort threshold:
                                   Minutes: """ + minutesM + "\n" + "Seconds: " + secondsM + "\n");

                }
                else {
                    // Print special line for mergesort
                    System.out.println("""
                                   Sorting time for merge sort:
                                   Minutes: """ + minutesM + "\n" + "Seconds: " + secondsM + "\n");

                }
                
                System.out.println("-Process finished-\nOutputs written to " + filePath + "-java" + " and " + filePath + "-mine"+ "\n");
                // The program ran successfully
                
            } catch (IOException e) {
                System.out.println("ERROR: Could not write to outfile");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error: file not found");
        }
    }
}
