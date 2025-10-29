/* T04n01 - Permutation Sort!  (a.k.a. Bogosort)
 * The name pretty much covers how it works (such as it works...):
 *
 * The algorithm:  Get a list of values.
 *                 Repeat  
 *                     Create a random permutation.
 *                 Until the permutation is in sorted order.
 *
 * As there are n! permutations of a list of n values, it's clear that
 * the running time of the algorithm is O(n!).  This implementation is
 * ever-so-slightly dangerous, because it uses Java's Collections.shuffle()
 * class method to create permutations.  It doesn't create a nice, orderly
 * sequence of permutations; it just randomly slaps one together.  Thus, it
 * takes an unpredictable number of iterations to stumble upon the correct
 * sequence.  Theoretically, it may never find it.
 */

import java.util.*;

public class T04n01
{

        // isSorted(list):  Returns true only if the list's content
        // is in ascending order.

    public static boolean isSorted (ArrayList<Integer> list)
    {
        for (int i = 0; i < list.size()-1; i++) {
            //if ( ((Integer)list.get(i)).compareTo(list.get(i+1)) > 0 ) {
            if ( list.get(i).compareTo(list.get(i+1)) > 0 ) {
                return false;
            }
        }
        return true;
    }

        // permutationSort(list):  Performs BogoSort by repeatly
        // permuting the list's content until it is in ascending order.
        // Note that it is theoretically possible that this routine
        // will never complete.

    public static int permutationSort (ArrayList<Integer> list)
    {
        int permutations = 0;  // the quantity of permutations generated

        while (!isSorted(list)) {
            Collections.shuffle(list);  // generates a random permutation
            permutations++;
        }
        return permutations;
    }

        // startTiming(): Returns the current value of the system's
        // nanosecond-granularity time counter.

    public static long startTiming ()
    {
        System.gc();  // Suggests that garbage should be collected first
        return System.nanoTime();
    }

        // stopTiming(time): Returns the difference between the given
        // nanosecond-granularity time value and the current time,
        // in seconds.

    public static double stopTiming (long startingTime)
    {
        long elapsedTime = System.nanoTime() - startingTime;
        return elapsedTime / 1_000_000_000.0;    // return seconds
    }


    public static void main (String[] args)
    {
        ArrayList<Integer> list; // value to be slowly sorted
        long     startTime;      // time the sorting begins (nanoseconds)
        double   duration;       // total duration of the sorting (seconds)
        int      permutations;   // quantity of permutations generated
        int      minutes;        // qty of whole minutes the sorting required
        double   seconds;        // additional seconds the sorting required

        if (args.length == 0) {
            System.out.println("\nThis sorts a (small!) list of values using "
                             + "Permutation Sort, an O(n!) sort.\n");
            System.out.println("Usage: java T04n01 <element1> .. <elementN>");
            System.exit(1);
        }

        list = new ArrayList<Integer>(args.length);
        for (int i = 0; i<args.length; i++) {
            list.add(i, Integer.parseInt(args[i]));
        }

        System.out.println("\nCalling PermutationSort on a list of size " 
                         + list.size() + ": " + list.toString());

        startTime = startTiming();
	permutations = permutationSort(list);
        duration = stopTiming(startTime);
        minutes = (int)duration / 60;
        seconds = (int)duration % 60 + (duration - (int)duration);

        System.out.println("\nFinished!  The sorted list: " + list.toString() 
                         + "\n\n" + permutations + " permutations "
                         + "and " + minutes + " minutes, " 
                         + seconds + " seconds\nwere required to "
                         + "complete the sort.");
    } // main
} // T04n01