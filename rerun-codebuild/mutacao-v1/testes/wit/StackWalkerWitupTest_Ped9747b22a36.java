/* @test    @summary Test StackWalker.getInstance(Set<Option> options, int estimateDepth) for valid and invalid inputs    @run main StackWalkerWitupTest_Ped9747b22a36 */

import java.lang.StackWalker;
import java.lang.StackWalker.Option;
import java.util.EnumSet;
import java.util.Set;

public class StackWalkerWitupTest_Ped9747b22a36 {

    public static void main(String[] args) {
        testValidEstimateDepth();
        testInvalidEstimateDepth();
        testNullOptions();
    }

    private static void testValidEstimateDepth() {
        try {
            Set<Option> options = EnumSet.of(Option.RETAIN_CLASS_REFERENCE);
            int estimateDepth = 10;
            StackWalker sw = StackWalker.getInstance(options, estimateDepth);
            if (sw == null) {
                throw new AssertionError("Expected non-null StackWalker instance for valid inputs");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid inputs: " + e);
        }
    }

    private static void testInvalidEstimateDepth() {
        try {
            Set<Option> options = EnumSet.of(Option.RETAIN_CLASS_REFERENCE);
            int estimateDepth = 0;
            StackWalker.getInstance(options, estimateDepth);
            throw new AssertionError("Expected IllegalArgumentException for estimateDepth <= 0");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    private static void testNullOptions() {
        try {
            int estimateDepth = 10;
            StackWalker.getInstance(null, estimateDepth);
            throw new AssertionError("Expected NullPointerException for null options");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
