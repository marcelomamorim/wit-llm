/* @test    @summary Test StackWalker.getInstance with valid options and estimateDepth    @run main StackWalkerWitupTest_Ped9747b22a36 */

import java.lang.StackWalker;
import java.lang.StackWalker.Option;
import java.util.EnumSet;
import java.util.Set;

public class StackWalkerWitupTest_Ped9747b22a36 {
    public static void main(String[] args) {
        testGetInstanceWithValidOptionsAndEstimateDepth();
        testGetInstanceWithZeroEstimateDepth();
        testGetInstanceWithNegativeEstimateDepth();
    }

    private static void testGetInstanceWithValidOptionsAndEstimateDepth() {
        try {
            Set<Option> options = EnumSet.of(Option.RETAIN_CLASS_REFERENCE);
            int estimateDepth = 10;
            StackWalker sw = StackWalker.getInstance(options, estimateDepth);
            if (sw == null) {
                throw new AssertionError("Expected non-null StackWalker instance");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testGetInstanceWithValidOptionsAndEstimateDepth", e);
        }
    }

    private static void testGetInstanceWithZeroEstimateDepth() {
        try {
            Set<Option> options = EnumSet.of(Option.RETAIN_CLASS_REFERENCE);
            int estimateDepth = 0;
            StackWalker sw = StackWalker.getInstance(options, estimateDepth);
            throw new AssertionError("Expected IllegalArgumentException for estimateDepth=0");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testGetInstanceWithZeroEstimateDepth", e);
        }
    }

    private static void testGetInstanceWithNegativeEstimateDepth() {
        try {
            Set<Option> options = EnumSet.of(Option.RETAIN_CLASS_REFERENCE);
            int estimateDepth = -5;
            StackWalker sw = StackWalker.getInstance(options, estimateDepth);
            throw new AssertionError("Expected IllegalArgumentException for negative estimateDepth");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testGetInstanceWithNegativeEstimateDepth", e);
        }
    }
}
