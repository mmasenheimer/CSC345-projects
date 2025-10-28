package project3;

import java.io.File;

public class Prog3 {

    public class dateTime {

        public int year;
        public int month;
        public int day;
        public int hour;
        public int minute;
        public int second;

    }

    public class message {

        public String sender;
        public dateTime time;
        public String content;
        
    }

    // Main function for input string parsing
    public void main(String[] args) {

        File file = new File(args[0]);

    }
    
}
