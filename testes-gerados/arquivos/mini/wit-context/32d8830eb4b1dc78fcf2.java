/*
 * @test
 * @summary Test Comparator.comparingInt behavior including null keyExtractor
 * @run main ComparatorWitupTest
 */

import java.util.Comparator;
import java.util.function.ToIntFunction;

public class ComparatorWitupTest {
    public static void main(String[] args) {
        testNullKeyExtractorThrows();
        testComparatorOrdering();
    }

    private static void testNullKeyExtractorThrows() {
        try {
            Comparator.comparingInt(null);
            throw new Error("Expected NullPointerException for null keyExtractor");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testComparatorOrdering() {
        ToIntFunction<String> lengthExtractor = new ToIntFunction<String>() {
            public int applyAsInt(String s) {
                return s.length();
            }
        };

        Comparator<String> comp = Comparator.comparingInt(lengthExtractor);

        // Compare strings of different lengths
        if (comp.compare("a", "bb") >= 0) {
            throw new Error("Comparator should order shorter string before longer string");
        }

        if (comp.compare("ccc", "bb") <= 0) {
            throw new Error("Comparator should order longer string after shorter string");
        }

        // Compare strings of same length
        if (comp.compare("aa", "bb") != 0) {
            throw new Error("Comparator should consider strings of same length equal");
        }
    }
}
