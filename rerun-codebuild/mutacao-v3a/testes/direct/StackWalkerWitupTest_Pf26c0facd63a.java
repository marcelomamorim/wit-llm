/* @test    @summary Test StackWalker.getInstance(Set<Option> options, int estimateDepth) with valid inputs    @run main StackWalkerWitupTest_Pf26c0facd63a */

import java.lang.StackWalker;
import java.util.EnumSet;

public class StackWalkerWitupTest_Pf26c0facd63a {

    public static void main(String[] args) {
        testGetInstanceWithValidOptionsAndEstimateDepth();
        testGetInstanceWithInvalidEstimateDepth();
    }

    static void testGetInstanceWithValidOptionsAndEstimateDepth() {
        EnumSet<StackWalker.Option> options = EnumSet.of(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        int estimateDepth = 10;
        try {
            StackWalker sw = StackWalker.getInstance(options, estimateDepth);
            if (sw == null) {
                throw new AssertionError("Expected non-null StackWalker instance");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    static void testGetInstanceWithInvalidEstimateDepth() {
        EnumSet<StackWalker.Option> options = EnumSet.of(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        int invalidEstimateDepth = 0;
        boolean thrown = false;
        try {
            StackWalker.getInstance(options, invalidEstimateDepth);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException for estimateDepth=0");
        }
    }
}
