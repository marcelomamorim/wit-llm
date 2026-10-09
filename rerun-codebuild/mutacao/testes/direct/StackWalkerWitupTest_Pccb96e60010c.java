/*
 * @test
 * @summary Test StackWalker.getInstance(Set<Option> options, int estimateDepth)
 * @run main StackWalkerWitupTest_Pccb96e60010c
 */

import java.lang.StackWalker;
import java.lang.StackWalker.Option;
import java.util.EnumSet;
import java.util.Set;

public class StackWalkerWitupTest_Pccb96e60010c {

    public static void main(String[] args) {
        testGetInstanceWithValidOptionsAndEstimate();
        testGetInstanceWithZeroEstimate();
        testGetInstanceWithNullOptions();
    }

    static void testGetInstanceWithValidOptionsAndEstimate() {
        Set<Option> options = EnumSet.of(Option.RETAIN_CLASS_REFERENCE);
        int estimateDepth = 10;
        StackWalker sw = StackWalker.getInstance(options, estimateDepth);
        if (sw == null) {
            throw new AssertionError("Expected non-null StackWalker instance");
        }
    }

    static void testGetInstanceWithZeroEstimate() {
        try {
            StackWalker.getInstance(EnumSet.of(Option.RETAIN_CLASS_REFERENCE), 0);
            throw new AssertionError("Expected IllegalArgumentException for estimateDepth=0");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testGetInstanceWithNullOptions() {
        try {
            StackWalker.getInstance(null, 10);
            throw new AssertionError("Expected NullPointerException for null options");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
