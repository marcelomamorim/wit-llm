/*
 * @test
 * @summary Test for BitSet.forEachRemaining with null action to verify NullPointerException
 * @run main BitSetWitupTest
 */

import java.util.BitSet;
import java.util.function.IntConsumer;

public class BitSetWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullAction();
    }

    public static void testForEachRemainingNullAction() {
        BitSet bs = new BitSet();
        // Initialize the BitSet with some bits set
        bs.set(0);
        bs.set(10);
        bs.set(100);
        
        try {
            bs.forEachRemaining(null);
            System.err.println("Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("NullPointerException correctly thrown.");
        }
    }
}
