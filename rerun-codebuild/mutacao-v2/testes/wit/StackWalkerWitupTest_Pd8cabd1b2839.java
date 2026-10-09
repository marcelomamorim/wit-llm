/*
 * @test
 * @summary Deterministic tests for StackWalker.getInstance(Set<Option>, int) covering exception paths
 * @run main StackWalkerWitupTest_Pd8cabd1b2839
 */

import java.lang.StackWalker;
import java.lang.StackWalker.Option;
import java.util.EnumSet;
import java.util.Set;

public class StackWalkerWitupTest_Pd8cabd1b2839 {
    public static void main(String[] args) {
        testIllegalArgumentExceptionForNonPositiveEstimateDepth();
        testNullPointerExceptionForNullOptionsAndPositiveEstimateDepth();
        testValidInstanceCreation();
    }

    private static void testIllegalArgumentExceptionForNonPositiveEstimateDepth() {
        try {
            StackWalker.getInstance(EnumSet.noneOf(Option.class), 0);
            throw new RuntimeException("Expected IllegalArgumentException for estimateDepth=0");
        } catch (IllegalArgumentException iae) {
            // expected
        }
        try {
            StackWalker.getInstance(EnumSet.noneOf(Option.class), -1);
            throw new RuntimeException("Expected IllegalArgumentException for estimateDepth=-1");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testNullPointerExceptionForNullOptionsAndPositiveEstimateDepth() {
        try {
            StackWalker.getInstance(null, 1);
            throw new RuntimeException("Expected NullPointerException for null options and positive estimateDepth");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testValidInstanceCreation() {
        Set<Option> options = EnumSet.of(Option.RETAIN_CLASS_REFERENCE);
        int estimateDepth = 5;
        StackWalker walker = StackWalker.getInstance(options, estimateDepth);
        if (walker == null) {
            throw new RuntimeException("StackWalker.getInstance returned null for valid inputs");
        }
    }
}
