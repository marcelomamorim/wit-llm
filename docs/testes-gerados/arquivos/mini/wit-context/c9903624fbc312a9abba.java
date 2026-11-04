/*
 * @test
 * @summary Test java.util.BitSet.set(int) for valid and invalid inputs
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {
    public static void main(String[] args) {
        testSetValidIndex();
        testSetNegativeIndex();
        System.out.println("All tests passed.");
    }

    private static void testSetValidIndex() {
        BitSet bs = new BitSet();
        // Set bit at index 0
        bs.set(0);
        if (!bs.get(0)) {
            throw new AssertionError("Bit at index 0 should be set");
        }

        // Set bit at a higher index
        int highIndex = 100;
        bs.set(highIndex);
        if (!bs.get(highIndex)) {
            throw new AssertionError("Bit at index " + highIndex + " should be set");
        }

        // Setting the same bit again should keep it set
        bs.set(highIndex);
        if (!bs.get(highIndex)) {
            throw new AssertionError("Bit at index " + highIndex + " should remain set");
        }
    }

    private static void testSetNegativeIndex() {
        BitSet bs = new BitSet();
        try {
            bs.set(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.contains("bitIndex < 0")) {
                throw new AssertionError("Exception message should mention 'bitIndex < 0', but was: " + msg);
            }
        }
    }
}
