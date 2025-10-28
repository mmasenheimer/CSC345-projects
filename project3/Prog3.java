package project3;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Prog3 {

    public class dateTime {

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

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }
}

    public class Message {

        public String sender;
        public dateTime time;
        public String content;

        public Message(String header, String content) {
            // Accepts a message in its entirety, presumming the sorting info is at the top in the form:
            // From MAILER-DAEMON Thu Oct  4 10:23:46 2007

            // From MAILER-DAEMON Thu Oct  4 10:23:46 2007
            // From dana@telia.com  Wed Oct  3 11:35:49 2007
            // From Adolfoinbredblithe@omegadrivers.net  Wed Oct  3 11:43:35 2007

            String[] parts = header.split(" ");

            this.sender = parts[1];
            this.content = content;

            // Parts for date/time (e.g., "Wed Oct 3 11:43:35 2007")
            String monthStr = parts[3];
            int day = Integer.parseInt(parts[4]);
            String[] hms = parts[5].split(":");
            int hour = Integer.parseInt(hms[0]);
            int minute = Integer.parseInt(hms[1]);
            int second = Integer.parseInt(hms[2]);
            int year = Integer.parseInt(parts[6]);

            // Convert month abbreviation to month number
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

            // Build LocalDateTime manually
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
    }

    public static ArrayList<Message> parseInput(Scanner fileScanner, String sortType) {

        ArrayList<Message> theMessages = new ArrayList<Message>();

        while (fileScanner.hasNextLine()){

            String line = fileScanner.nextLine();
            // Grab the current line in the file

            if (line.startsWith("From ")) {

                Message curr = new Message(line);

                theMessages.add(curr);
            }

        }

        return theMessages;


    }

    // Main function for input string parsing
    public void main(String[] args) {

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

            parseInput(scanner, sortType);

            
        } catch (FileNotFoundException e) {
            // Catch the error if the file is not found
            System.out.println("Error: file not found");
        }

    }
    
}
