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

    public static class dateTime {

    public int year;
    public int month;
    public int day;
    public int hour;
    public int minute;
    public int second;

    public dateTime(int year, int month, int day, int hour, int minute, int second) {
       this.year = year;
       this.month = month;
       this.day = day;
       this.hour = hour;
       this.minute = minute;
       this.second = second;
    }

}

    public static class Message implements Comparable<Message> {

        public String sender;
        public dateTime time;
        public String content;
        public String fullHeader;

        public Message(String header, String content) {
            // Accepts a message in its entirety, presumming the sorting info is at the top in the form:
            // From MAILER-DAEMON Thu Oct  4 10:23:46 2007

            // From MAILER-DAEMON Thu Oct  4 10:23:46 2007
            // From dana@telia.com  Wed Oct  3 11:35:49 2007
            // From Adolfoinbredblithe@omegadrivers.net  Wed Oct  3 11:43:35 2007

            String[] parts = header.split("\\s+");

            this.sender = parts[1];
            this.content = content;
            this.fullHeader = header;

            // Parts for date/time (e.g., "Wed Oct 3 11:43:35 2007")
            String monthStr = parts[3];
            int day = Integer.parseInt(parts[4]);
            String[] hms = parts[5].split(":");
            int hour = Integer.parseInt(hms[0]);
            int minute = Integer.parseInt(hms[1]);
            int second = Integer.parseInt(hms[2]);
            int year = Integer.parseInt(parts[6]);

            int month = 1;
            switch (monthStr) {
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
        }

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

        @Override
        public int compareTo(Message other) {
            // Compare by date/time
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

    public static ArrayList<Message> parseInput(Scanner fileScanner, String sortType) {

        ArrayList<Message> theMessages = new ArrayList<>();
        StringBuilder contentBuilder = new StringBuilder();
        String headerLine = null;

        while (fileScanner.hasNextLine()){

            String line = fileScanner.nextLine();
            // Grab the current line in the file

            if (line.startsWith("From ")) {

                if (headerLine != null) {
                    Message curr = new Message(headerLine, contentBuilder.toString());
                    theMessages.add(curr);
                }

                headerLine = line;
                contentBuilder = new StringBuilder();
            }
            else {

                if (contentBuilder.length() > 0) {
                    contentBuilder.append("\n");
                    // preserve the line breaks in the body
                }
                contentBuilder.append(line);
            }
        }

        if (headerLine != null) {
            Message curr = new Message(headerLine, contentBuilder.toString());
            theMessages.add(curr);
        }

        return theMessages;
    }

    // MY VERSION OF THE MESSAGE OBJECT SORT
    public static ArrayList<Message> mySortDate(ArrayList<Message> input) {

        quickSortByDate(input, 0, input.size() - 1, 12);
        return input;
    }


    public static void quickSortByDate(ArrayList<Message> input, int low, int high, int insertionSortWhen) {

        if (high - low < insertionSortWhen) {
            insertionSort(input, low, high);
            return;
        }

        if (low < high) {
            int mid = low + (high - low) / 2;
            Collections.swap(input, mid, high);

            Message pivotPosition = input.get(high);
            int curr = low - 1;

            for (int j = low; j < high; j++) {
                if (input.get(j).compareTo(pivotPosition) <= 0) {
                    curr++;
                    Collections.swap(input, curr, j);
                }
            }

            Collections.swap(input, curr+1, high);
            int pivotIndex = curr + 1;

            quickSortByDate(input, low, pivotIndex - 1, insertionSortWhen);
            quickSortByDate(input, pivotIndex + 1, high, insertionSortWhen);

        }
    }

    private static void insertionSort(ArrayList<Message> input, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            Message key = input.get(i);
            int j = i - 1;
            while (j >= low && input.get(j).compareTo(key) > 0) {
                input.set(j + 1, input.get(j));
                j--;
            }
            input.set(j + 1, key);
        }
    }

    public static ArrayList<Message> mySortSender(ArrayList<Message> input) {
    if (input.size() <= 1) {
        return input;
    }
    
    mergeSort(input, 0, input.size() - 1);
    return input;
}

private static void mergeSort(ArrayList<Message> input, int left, int right) {
    if (left < right) {
        int mid = left + (right - left) / 2;
        
        // Sort first and second halves
        mergeSort(input, left, mid);
        mergeSort(input, mid + 1, right);
        
        // Merge the sorted halves
        merge(input, left, mid, right);
    }
}

private static void merge(ArrayList<Message> input, int left, int mid, int right) {
    // Create temporary arrays
    int n1 = mid - left + 1;
    int n2 = right - mid;
    
    ArrayList<Message> leftArray = new ArrayList<>(n1);
    ArrayList<Message> rightArray = new ArrayList<>(n2);
    
    // Copy data to temp arrays
    for (int i = 0; i < n1; i++) {
        leftArray.add(input.get(left + i));
    }
    for (int j = 0; j < n2; j++) {
        rightArray.add(input.get(mid + 1 + j));
    }
    
    // Merge the temp arrays back
    int i = 0, j = 0;
    int k = left;
    
    while (i < n1 && j < n2) {
        if (leftArray.get(i).getSender().compareTo(rightArray.get(j).getSender()) <= 0) {
            input.set(k, leftArray.get(i));
            i++;
        } else {
            input.set(k, rightArray.get(j));
            j++;
        }
        k++;
    }
    
    // Copy remaining elements
    while (i < n1) {
        input.set(k, leftArray.get(i));
        i++;
        k++;
    }
    
    while (j < n2) {
        input.set(k, rightArray.get(j));
        j++;
        k++;
    }
}

    public static long startTiming () {
        // Taken from the 345 website
        System.gc();
        // Collect garbage
        return System.nanoTime();
        // Start timing
    }

    public static double stopTiming (long startingTime) {
        // Taken from the 345 website

        long elapsedTime = System.nanoTime() - startingTime;
        return elapsedTime / 1_000_000_000.0;
        // Return the time in seconds
    }

    // Main function for input string parsing
    public static void main(String[] args) {

        args = new String[] {"C:\\Users\\mmase\\OneDrive\\Semester 5\\CSC 345\\Projects\\project3\\output2.mbox", "sender"};

        int minutesJ = 0;
        long startTimeJ = 0;
        double secondsJ = 0.0;
        double durationJ = 0;

        int minutesM = 0;
        long startTimeM = 0;
        double secondsM = 0.0;
        double durationM = 0;

        if (args.length < 2) {
            // If there are an invalid number of arguments
            System.out.println("Error: must have 2 arguments in the format format: java MboxSorter <filename> <sortType>");
            return;
        }

        String filePath = args[0];
        String sortType = args[1].toLowerCase();

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

            ArrayList<Message> javaResult = parseInput(scanner, sortType);
            ArrayList<Message> myResult = new ArrayList<>(javaResult);

            if (sortType.equals("date")) {
                startTimeJ = startTiming();
                // Sort by date

                Collections.sort(javaResult);

                durationJ = stopTiming(startTimeJ);

                minutesJ = (int)durationJ / 60;
                secondsJ = (int)durationJ % 60 + (durationJ - (int)durationJ);

                // MY SORTING ALGORITHM FOR DATE

                startTimeM = startTiming();
                // Sort by date

                mySortDate(myResult);

                durationM = stopTiming(startTimeM);

                minutesM = (int)durationM / 60;
                secondsM = (int)durationM % 60 + (durationM - (int)durationM);

            }

            else if (sortType.equals("sender")) {
                startTimeJ = startTiming();
                // Sort by sender

                Collections.sort(javaResult, (Message m1, Message m2) -> m1.getSender().compareTo(m2.getSender()));

                durationJ = stopTiming(startTimeJ);

                minutesJ = (int)durationJ / 60;
                secondsJ = (int)durationJ % 60 + (durationJ - (int)durationJ);

                // MY SORTING ALGORITHM FOR SENDER

                startTimeM = startTiming();
                // Sort by date

                mySortSender(myResult);

                durationM = stopTiming(startTimeM);

                minutesM = (int)durationM / 60;
                secondsM = (int)durationM % 60 + (durationM - (int)durationM);

            }

            try {
                // Write the files
                PrintWriter writerJava = new PrintWriter(new FileWriter(filePath + "-java"));
                PrintWriter writerMine = new PrintWriter(new FileWriter(filePath + "-mine"));

                for (int i = 0; i < javaResult.size(); i++) {

                    Message msgJ = javaResult.get(i);
                    Message msgM = myResult.get(i);

                    writerJava.println(msgJ.getHeader());
                    writerJava.print(msgJ.getContent());

                    writerMine.println(msgM.getHeader());
                    writerMine.print(msgM.getContent());
    
                    if (i < javaResult.size() - 1) {
                        writerJava.println();  // Only print blank line if not the last message
                        writerMine.println();
                    }
                    else {
                        writerJava.println();
                        writerMine.println();
                    }
                }

                writerMine.close();
                writerJava.close();

                System.out.println("""
                                   Sorting time for Java collections.sort:
                                   Minutes: """ + minutesJ + "\n" + "Seconds: " + secondsJ + "\n");
                
                if (sortType.equals("date")) {
                    System.out.println("""
                                   Sorting time for quicksort/insertion sort threshold:
                                   Minutes: """ + minutesM + "\n" + "Seconds: " + secondsM + "\n");

                }
                else {
                    System.out.println("""
                                   Sorting time for merge sort:
                                   Minutes: """ + minutesM + "\n" + "Seconds: " + secondsM + "\n");

                }
                
                System.out.println("-Process finished-\nOutputs written to " + filePath + "-java" + " and " + filePath + "-mine"+ "\n");
                
            } catch (IOException e) {
                System.out.println("ERROR: Could not write to outfile");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error: file not found");
        }
    }
}
