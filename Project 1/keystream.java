
public class keystream {

    public static Integer findKeyStream(Integer[] deck) {

    moveJokerA(deck);

    moveJokerB(deck);
    

    tripleCut(deck);
    

    countCut(deck);
    
    int topCardValue = deck[0];
    if (topCardValue == 27 || topCardValue == 28) {
        topCardValue = 27;
    }

    int keystreamIndex = topCardValue;
    
    // Handle the case where we might go past the end of the deck
    if (keystreamIndex >= deck.length) {
        keystreamIndex = keystreamIndex % deck.length;
    }
    
    int keystreamCard = deck[keystreamIndex];

    // If keystream card is a joker, repeat the whole process
    if (keystreamCard == 27 || keystreamCard == 28) {
        // Don't create a copy - continue with the modified deck!
        return findKeyStream(deck);
    }

    return keystreamCard;
}
    
    private static void moveJokerA(Integer[] deck) {
        int pos = findCard(deck, 27);
        int nextPos = (pos + 1) % deck.length;
        
        // Simple swap with the next card (circular)
        int temp = deck[pos];
        deck[pos] = deck[nextPos];
        deck[nextPos] = temp;
    }
    
    private static void moveJokerB(Integer[] deck) {
    int pos = findCard(deck, 28);
    
    // Move two positions down by doing two single swaps
    // First swap: move one position down
    int nextPos1 = (pos + 1) % deck.length;
    Integer temp = deck[pos];
    deck[pos] = deck[nextPos1];
    deck[nextPos1] = temp;
    
    // Update position to where Joker B is now
    pos = nextPos1;
    
    // Second swap: move one more position down from new position
    int nextPos2 = (pos + 1) % deck.length;
    temp = deck[pos];
    deck[pos] = deck[nextPos2];
    deck[nextPos2] = temp;
}
    
    private static void tripleCut(Integer[] deck) {
        int jokerAPos = findCard(deck, 27);
        int jokerBPos = findCard(deck, 28);
        
        // Find first and second joker positions
        int firstJoker = Math.min(jokerAPos, jokerBPos);
        int secondJoker = Math.max(jokerAPos, jokerBPos);
        
        // Create new deck: [after second joker] + [first joker to second joker] + [before first joker]
        Integer[] newDeck = new Integer[deck.length];
        int newIndex = 0;
        
        // Copy cards after second joker
        for (int i = secondJoker + 1; i < deck.length; i++) {
            newDeck[newIndex++] = deck[i];
        }
        
        // Copy cards from first joker to second joker (inclusive)
        for (int i = firstJoker; i <= secondJoker; i++) {
            newDeck[newIndex++] = deck[i];
        }
        
        // Copy cards before first joker
        for (int i = 0; i < firstJoker; i++) {
            newDeck[newIndex++] = deck[i];
        }
        
        // Copy back to original array
        System.arraycopy(newDeck, 0, deck, 0, deck.length);
    }

    
    private static void countCut(Integer[] deck) {
        int bottomCard = deck[deck.length - 1];

        // Treat jokers as 27
        if (bottomCard == 27 || bottomCard == 28) {
            bottomCard = 27;
        }

        // If bottom card value >= deck length - 1, skip (no cut needed)
        if (bottomCard >= deck.length - 1) return;

        // Create new deck arrangement
        Integer[] newDeck = new Integer[deck.length];
        int newIndex = 0;

        // Copy cards after the first bottomCard cards (from position bottomCard to length-2)
        for (int i = bottomCard; i < deck.length - 1; i++) {
            newDeck[newIndex++] = deck[i];
        }

        // Copy the first bottomCard cards
        for (int i = 0; i < bottomCard; i++) {
            newDeck[newIndex++] = deck[i];
        }

        // Keep the bottom card in place
        newDeck[deck.length - 1] = deck[deck.length - 1];

        // Copy back to original deck
        System.arraycopy(newDeck, 0, deck, 0, deck.length);
    }
    
    private static int findCard(Integer[] deck, int card) {
        for (int i = 0; i < deck.length; i++) {
            if (deck[i] == card) {
                return i;
            }
        }
        return -1;

    }
}