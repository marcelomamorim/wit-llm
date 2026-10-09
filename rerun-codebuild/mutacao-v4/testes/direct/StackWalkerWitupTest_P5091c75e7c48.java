/* @test
   @summary Testa o método getInstance(Set<Option> options, int estimateDepth) do StackWalker.
   @run main StackWalkerWitupTest_P5091c75e7c48
 */
import java.util.EnumSet;
import java.util.Set;
import java.lang.StackWalker;
import java.lang.StackWalker.Option;

public class StackWalkerWitupTest_P5091c75e7c48 {
    public static void main(String[] args) {
        testGetInstanceWithValidOptions();
        testGetInstanceWithZeroEstimateDepth();
        testGetInstanceWithNegativeEstimateDepth();
        testGetInstanceWithNullOptions();
    }

    static void testGetInstanceWithValidOptions() {
        Set<Option> options = EnumSet.of(Option.RETAIN_CLASS_REFERENCE);
        int estimateDepth = 10;
        StackWalker sw = StackWalker.getInstance(options, estimateDepth);
        if (sw == null) {
            throw new AssertionError("getInstance returned null with valid options");
        }
    }

    static void testGetInstanceWithZeroEstimateDepth() {
        try {
            StackWalker.getInstance(EnumSet.of(Option.RETAIN_CLASS_REFERENCE), 0);
            throw new AssertionError("Expected IllegalArgumentException for estimateDepth=0");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testGetInstanceWithNegativeEstimateDepth() {
        try {
            StackWalker.getInstance(EnumSet.of(Option.RETAIN_CLASS_REFERENCE), -5);
            throw new AssertionError("Expected IllegalArgumentException for negative estimateDepth");
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
