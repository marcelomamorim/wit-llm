/*
 * @test
 * @summary Tests for java.lang.StackWalker.getInstance(Set<Option> options, int estimateDepth)
 * @run main StackWalkerWitupTest
 */

import java.lang.StackWalker;
import java.lang.StackWalker.Option;
import java.util.EnumSet;

public class StackWalkerWitupTest {

    public static void main(String[] args) {
        testGetInstanceWithZeroEstimateDepth();
        testGetInstanceWithNullOptions();
    }

    private static void testGetInstanceWithZeroEstimateDepth() {
        try {
            StackWalker.getInstance(null, 0);
            System.err.println("Expected IllegalArgumentException for estimateDepth=0 not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            System.err.println("Unexpected exception for estimateDepth=0: " + e);
        }
    }

    private static void testGetInstanceWithNullOptions() {
        try {
            StackWalker.getInstance(null, 1);
            System.err.println("Expected NullPointerException for null options not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            System.err.println("Unexpected exception for null options: " + e);
        }
    }
}
