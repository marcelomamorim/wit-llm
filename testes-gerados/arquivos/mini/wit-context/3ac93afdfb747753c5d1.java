/*
 * @test
 * @summary Test Comparator.comparingDouble with valid and null keyExtractor
 * @run main ComparatorWitupTest
 */

import java.util.Comparator;
import java.util.function.ToDoubleFunction;

public class ComparatorWitupTest {
    public static void main(String[] args) {
        testComparingDoubleWithValidKeyExtractor();
        testComparingDoubleWithNullKeyExtractor();
    }

    private static void testComparingDoubleWithValidKeyExtractor() {
        ToDoubleFunction<String> lengthExtractor = new ToDoubleFunction<String>() {
            public double applyAsDouble(String s) {
                return s.length();
            }
        };

        Comparator<String> comp = Comparator.comparingDouble(lengthExtractor);

        // Compare strings by length
        int result1 = comp.compare("a", "bb");
        int result2 = comp.compare("ccc", "cc");
        int result3 = comp.compare("dd", "dd");

        if (!(result1 < 0)) {
            throw new AssertionError("Expected 'a' < 'bb' by length");
        }
        if (!(result2 > 0)) {
            throw new AssertionError("Expected 'ccc' > 'cc' by length");
        }
        if (result3 != 0) {
            throw new AssertionError("Expected 'dd' == 'dd' by length");
        }
    }

    private static void testComparingDoubleWithNullKeyExtractor() {
        try {
            Comparator.comparingDouble(null);
            throw new AssertionError("Expected NullPointerException for null keyExtractor");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
