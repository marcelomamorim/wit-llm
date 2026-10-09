/*
 * @test
 * @summary Tests for java.lang.StackWalker getInstance(Set<Option> options, int estimateDepth)
 * @run main StackWalkerWitupTest_Pf26c0facd63a
 */

import java.lang.StackWalker;
import java.lang.StackWalker.Option;
import java.util.EnumSet;
import java.util.Set;

public class StackWalkerWitupTest_Pf26c0facd63a {

    public static void main(String[] args) {
        testGetInstanceWithZeroEstimateDepth();
        testGetInstanceWithNullOptions();
    }

    public static void testGetInstanceWithZeroEstimateDepth() {
        try {
            StackWalker.getInstance(null, 0);
            throw new AssertionError("Expected IllegalArgumentException for estimateDepth=0");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e, e);
        }
    }

    public static void testGetInstanceWithNullOptions() {
        try {
            StackWalker.getInstance(null, 10);
            // Should succeed, options can be null
        } catch (NullPointerException e) {
            throw new AssertionError("NullPointerException thrown unexpectedly", e);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e, e);
        }
    }
}
