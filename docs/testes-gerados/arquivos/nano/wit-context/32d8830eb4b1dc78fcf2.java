/* @test    @summary Test for java.util.Comparator comparingInt method handling null keyExtractor    @run main ComparatorWitupTest */

import java.util.Comparator;
import java.util.Objects;

public class ComparatorWitupTest {

    public static void main(String[] args) {
        testComparingIntWithNull();
        testComparingIntWithValidFunction();
    }

    static void testComparingIntWithNull() {
        try {
            Comparator<Integer> c = Comparator.comparingInt(null);
            // If no exception, test fails
            throw new AssertionError("Expected NullPointerException for null keyExtractor");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        }
    }

    static void testComparingIntWithValidFunction() {
        Comparator<String> comparator = Comparator.comparingInt(String::length);
        int result1 = comparator.compare("abc", "ab");
        int result2 = comparator.compare("a", "abc");
        int result3 = comparator.compare("abc", "abc");
        // Validate comparison results
        if (!(result1 > 0 && result2 < 0 && result3 == 0)) {
            throw new AssertionError("Comparison results do not match expected behavior");
        }
    }
}
