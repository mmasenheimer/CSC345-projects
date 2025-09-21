/*
 * Class: ConvertDeck.java
 * Name: Michael Masenheimer
 * Package: Project 1 (No inheritence), relies on ConvertDeck.java and keystream.java
 * 
 * Purpose: This class takes in a string representation of a half-deck of cards, and converts it
 * into an Intger[] array with the number representations of all of the cards. This makes
 * it easier to process keystream values in the keystream.java class.
 * 
 * There are no public constants and no public variables (fields) declared in this class.
 * All variables are declared as local variables inside methods.
 * 
 * ConvertDeck also has no explicitly defined constructors
 */

public class ConvertDeck {

    /*
     * Method: cardToNumber
     * Purpose: Converts a single card string (e.g., "A♣", "10D", "JA") into its corresponding integer value.
     * Pre-condition: card must be a valid card string: "A"-"K" followed by "C" or "D", or "JA"/"JB" for jokers.
     * Post-condition: None (input string is not modified).
     * Return value: Integer representing the card: 1-13 for Clubs, 14-26 for Diamonds, 27 for Joker A, 28 for Joker B.
     * Parameters:
     *   - card (in): String representing a single card.
    */

    public static Integer cardToNumber (String card) {

        if (card.equals("JA")) return 27;
        if (card.equals("JB")) return 28;
        // Jokers A, B mapped to 27, 28 respectivley
        
        char suit = card.charAt(card.length() - 1);
        String rankStr = card.substring(0, card.length() - 1);
        // The last character is the suit, remaining string is the rank
        
        int rank;
        switch (rankStr) {
            // This switch handles Ace, Jack, Queen, King
            case "A": rank = 1; break;
            case "J": rank = 11; break;
            case "Q": rank = 12; break;
            case "K": rank = 13; break;
            default: rank = Integer.parseInt(rankStr); break;

        }
        
        int suitOffset;
        switch (suit) {
            // This switch handles the suit of the card
            case 'C': suitOffset = 0; break;
            case 'D': suitOffset = 13; break;
            default: suitOffset = 0; break;
        }

        return rank + suitOffset;
        // Return the final card value

    }

    /*
     * Method: startConvert
     * Purpose: Converts a space-separated string of card codes into an array of integers.
     * Pre-condition: preDeck must contain valid card strings separated by spaces.
     * Post-condition: Returns a new array of integers representing the deck; input string is unchanged.
     * Return value: Integer array representing the deck of cards in numeric form.
     * Parameters:
     *   - preDeck (in): String representing the full deck, with cards separated by spaces.
    */
    public static Integer[] startConvert (String preDeck) {
        Integer[] result = new Integer[preDeck.split(" ").length];
        String[] convertArr = preDeck.split(" ");
        // Split the deck string by space and init a new array to put the ints into

        for (int i = 0; i < convertArr.length; i++) {
            // Iterate over each card
            result[i] = ConvertDeck.cardToNumber(convertArr[i]);
        }

        return result;
    }
}