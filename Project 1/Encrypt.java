import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Encrypt {

    public static Integer[] encrypt (String message, Integer[] deck) {

        HashMap<Character, Integer> map = new HashMap<>();

        for (char c = 'A'; c <= 'Z'; c++) {
            map.put(c, c - 'A' + 1);
        }

        String converted = "";

        for (int i = 0; i < message.length(); i++) {

            if (Character.isLetter(message.charAt(i))) {
                converted += Character.toUpperCase(message.charAt(i));
            }
        }

        int padding = (5 - (converted.length() % 5)) % 5;
        converted += "X".repeat(padding);

       Integer[] numbered = new Integer[converted.length()];

        for (int i = 0; i < converted.length(); i++) {
            numbered[i] = map.get(converted.charAt(i));
        }

        int numIterations = (numbered.length);

        Integer[] keyStreamValues = new Integer[numIterations];

        int index = 0;

        Integer[] finalResultArr = new Integer[keyStreamValues.length];

        while (index < numIterations) {
            keyStreamValues[index] = keystream.findKeyStream(deck);
            index += 1;
        }

        // Add keystream values
        for (int i = 0; i < keyStreamValues.length; i++) {
            int temp = numbered[i] + keyStreamValues[i];

            if (temp > 26) {
                temp -= 26;
            }
            finalResultArr[i] = temp;
        }

        // Convert back to letters
        StringBuilder finalResult = new StringBuilder();

        for (int num : finalResultArr) {
            if (num >= 1 && num <= 26) {
                char c = (char) ('A' + num - 1);
                finalResult.append(c);
            }
        }

        System.out.println(finalResult);
        return deck;

    }

    public static void main (String[] args) throws FileNotFoundException{
        File inputMessages = new File("messages.txt");
        File inputDeck = new File("test1.txt");

        Scanner messageScanner = new Scanner(inputMessages);
        Scanner deckScanner = new Scanner(inputDeck);

        String theDeck = deckScanner.nextLine();
        Integer[] convertedDeck = ConvertDeck.startConvert(theDeck);

        while (messageScanner.hasNextLine()) {

            String curr = messageScanner.nextLine();
            encrypt(curr, convertedDeck);
        }

        messageScanner.close();
        deckScanner.close();

    }

}