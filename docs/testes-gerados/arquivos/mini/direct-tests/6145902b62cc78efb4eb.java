/*
 * @test
 * @summary Test StackWalker.getInstance(Set<Option>, int) with valid and invalid estimateDepth
 * @run main StackWalkerWitupTest
 */

import java.lang.StackWalker;
import java.lang.StackWalker.Option;
import java.util.EnumSet;
import java.util.Set;

public class StackWalkerWitupTest {
    public static void main(String[] args) {
        testValidEstimateDepth();
        testInvalidEstimateDepth();
    }

    private static void testValidEstimateDepth() {
        Set<Option> options = EnumSet.noneOf(Option.class);
        int estimateDepth = 1;
        StackWalker walker = StackWalker.getInstance(options, estimateDepth);
        if (walker == null) {
            throw new AssertionError("StackWalker.getInstance returned null for valid estimateDepth");
        }
    }

    private static void testInvalidEstimateDepth() {
        Set<Option> options = EnumSet.noneOf(Option.class);
        try {
            StackWalker.getInstance(options, 0);
            throw new AssertionError("Expected IllegalArgumentException for estimateDepth=0");
        } catch (IllegalArgumentException expected) {
        }
        try {
            StackWalker.getInstance(options, -1);
            throw new AssertionError("Expected IllegalArgumentException for estimateDepth=-1");
        } catch (IllegalArgumentException expected) {
        }
    }
}
