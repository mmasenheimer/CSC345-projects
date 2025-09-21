/*
 * Class: keystream.java
 * Name: Michael Masenheimer
 * Package: Project 1 (No inheritence), relies on ConvertDeck.java and keystream.java
 * 
 * Purpose: This class takes in an integer array representation of a half deck and generates
 * keystream values for the deck, mutating it. It first moves both jokers, then performs a
 * "triple cut", finally moving the top k cards to the back (depending on what the last card is),
 * then takes the ith + 1 card as the keystream value.
 * 
 * There are no public constants and no public variables (fields) declared in this class.
 * All variables are declared as local variables inside methods.
 * 
 * keystream also has no explicitly defined constructors
 */

public class keystream {

    /*
     * Method: findKeyStream
     * Purpose: Generates the next keystream value using the classic Solitaire cipher steps.
     * Pre-condition: deck must contain integers 1-28 representing 
     * a full deck with jokers as 27 and 28.
     * Post-condition: deck is modified according to Solitaire steps.
     * Return value: next keystream integer (1-26, excluding jokers).
     * Parameters:
     *   - deck (in/out): Integer array representing the current deck; modified in-place.
    */
    public static Integer findKeyStream(Integer[] deck) {

    moveJokerA(deck);

    moveJokerB(deck);
    
    tripleCut(deck);

    countCut(deck);
    
    int topCardValue = deck[0];
    // Take the top card of the deck

    if (topCardValue == 27 || topCardValue == 28) {
        // If the top card ends up being a joker, set to joker a regardless
        topCardValue = 27;
    }

    int keystreamIndex = topCardValue;
    
    if (keystreamIndex >= deck.length) {
        // Handle the case where we might go past the end of the deck
        keystreamIndex = keystreamIndex % deck.length;
    }
    
    int keystreamCard = deck[keystreamIndex];
    // Represents the card with the keystream value

    if (keystreamCard == 27 || keystreamCard == 28) {
        // If keystream card is a joker, repeat the whole process
        return findKeyStream(deck);
    }

    return keystreamCard;
}

    /*
     * Method: moveJokerA
     * Purpose: Moves Joker A (27) one position down in a circular manner.
     * Pre-condition: deck contains integer 27 representing Joker A.
     * Post-condition: Joker A is moved one position down.
     * Return value: None.
     * Parameters:
     *   - deck (in/out): Integer array representing the deck; modified in-place.
    */
    private static void moveJokerA(Integer[] deck) {

        int pos = findCard(deck, 27);
        int nextPos = (pos + 1) % deck.length;
        // Call separate method finding the first joker and its next position
        
        int temp = deck[pos];
        deck[pos] = deck[nextPos];
        deck[nextPos] = temp;
        // Simple swap with the next card (circular)
    }

    /*
     * Method: moveJokerB
     * Purpose: Moves Joker B (28) two positions down in a circular manner.
     * Pre-condition: deck contains integer 28 representing Joker B.
     * Post-condition: Joker B is moved two positions down.
     * Return value: None.
     * Parameters:
     *   - deck (in/out): Integer array representing the deck; modified in-place.
    */
    private static void moveJokerB(Integer[] deck) {
    int pos = findCard(deck, 28);
    // Find the second joker
    
    // Move two positions down by doing two single swaps
    int nextPos1 = (pos + 1) % deck.length;
    Integer temp = deck[pos];
    deck[pos] = deck[nextPos1];
    deck[nextPos1] = temp;
    // First swap: move one position down
    
    pos = nextPos1;
    // Update position to where Joker B is now
    
    int nextPos2 = (pos + 1) % deck.length;
    temp = deck[pos];
    deck[pos] = deck[nextPos2];
    deck[nextPos2] = temp;
    // Second swap: move one more position down from new position
}

    /*
     * Method: tripleCut
     * Purpose: Performs the triple cut around Joker A and Joker B.
     * Pre-condition: deck contains integers 27 and 28 representing jokers.
     * Post-condition: Cards are rearranged according to the triple cut rule.
     * Return value: None.
     * Parameters:
     *   - deck (in/out): Integer array representing the deck; modified in-place.
    */
    private static void tripleCut(Integer[] deck) {
        int jokerAPos = findCard(deck, 27);
        int jokerBPos = findCard(deck, 28);
        // Find both jokers
        
        int firstJoker = Math.min(jokerAPos, jokerBPos);
        int secondJoker = Math.max(jokerAPos, jokerBPos);
        // Find first and second joker positions
        
        Integer[] newDeck = new Integer[deck.length];
        int newIndex = 0;
        // Create new deck: [after second joker] + [first joker to second joker] + [before first joker]
        
        for (int i = secondJoker + 1; i < deck.length; i++) {
            // Copy cards after second joker
            newDeck[newIndex++] = deck[i];
        }
        
      
        for (int i = firstJoker; i <= secondJoker; i++) {
              // Copy cards from first joker to second joker (this is inclusive)
            newDeck[newIndex++] = deck[i];
        }
        
        for (int i = 0; i < firstJoker; i++) {
            // Copy cards before th first joker
            newDeck[newIndex++] = deck[i];
        }
        
        System.arraycopy(newDeck, 0, deck, 0, deck.length);
        // Copy back to original array
    }

    /*
     * Method: countCut
     * Purpose: Performs the count cut using the value of the bottom card.
     * Pre-condition: deck contains integers 1-28 representing a full deck.
     * Post-condition: Top n cards are moved just above the bottom card.
     * Return value: None.
     * Parameters:
     *   - deck (in/out): Integer array representing the deck; modified in-place.
    */
    private static void countCut(Integer[] deck) {
        int bottomCard = deck[deck.length - 1];

        if (bottomCard == 27 || bottomCard == 28) {
            // Both of the jokers are represented as a 27 here
            bottomCard = 27;
        }

        if (bottomCard >= deck.length - 1) return;
        // If bottom card value >= deck length - 1, then  skip

        Integer[] newDeck = new Integer[deck.length];
        int newIndex = 0;
        // Create new deck arrangement

        for (int i = bottomCard; i < deck.length - 1; i++) {
            // Copy cards after the first bottomCard cards
            newDeck[newIndex++] = deck[i];
        }

        for (int i = 0; i < bottomCard; i++) {
            // Copy the first bottomCard cards
            newDeck[newIndex++] = deck[i];
        }

        newDeck[deck.length - 1] = deck[deck.length - 1];
        // Keep the bottom card in place

      
        System.arraycopy(newDeck, 0, deck, 0, deck.length);
        // Copy back to the original deck
    }

    /*
     * Method: findCard
     * Purpose: Finds the position of a specific card in the deck.
     * Pre-condition: deck contains the integer card to search for.
     * Post-condition: deck remains unchanged.
     * Return value: Index of the card in the deck, or -1 if not found.
     * Parameters:
     *   - deck (in): Integer array representing the deck; not modified.
     *   - card (in): Integer representing the card to find.
    */
    private static int findCard(Integer[] deck, int card) {
        for (int i = 0; i < deck.length; i++) {
            if (deck[i] == card) {
                // Find where the input card is
                return i;
            }
        }
        return -1;
        // This shouldn't happen if the deck is valid (it should be)

    }
}