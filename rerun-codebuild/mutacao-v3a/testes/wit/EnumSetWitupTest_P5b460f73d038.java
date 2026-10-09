/* @test    @summary Test EnumSet.range for valid and invalid inputs    @run main EnumSetWitupTest_P5b460f73d038 */

import java.util.EnumSet;
import java.util.NoSuchElementException;

public class EnumSetWitupTest_P5b460f73d038 {

    public static void main(String[] args) {
        testRangeValid();
        testRangeInvalid();
    }

    private static void testRangeValid() {
        try {
            // Define an enum for testing
            enum Color { RED, GREEN, BLUE }

            EnumSet<Color> set = EnumSet.range(Color.RED, Color.GREEN);
            boolean containsRed = set.contains(Color.RED);
            boolean containsGreen = set.contains(Color.GREEN);
            boolean containsBlue = set.contains(Color.BLUE);
            if (!containsRed || !containsGreen || containsBlue) {
                throw new AssertionError("Unexpected set contents in valid range test")
            }
        } catch (Throwable t) {
            throw new AssertionError("Exception in valid range test: " + t);
        }
    }

    private static void testRangeInvalid() {
        enum Color { RED, GREEN, BLUE }
        try {
            // from.compareTo(to) > 0 should throw IllegalArgumentException
            EnumSet<Color> result = EnumSet.range(Color.GREEN, Color.RED);
            throw new AssertionError("Expected IllegalArgumentException not thrown")
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t);
        }
        try {
            // Simulate ClassCastException by passing incompatible enum types
            // Not directly possible here because method is generic, so skip this case
            // Alternatively, test with a different enum type if reflection or casting were used
            // but per constraints, skip as no explicit cross-type test is feasible
        } catch (Throwable t) {
            // No action
        }
    }
}
