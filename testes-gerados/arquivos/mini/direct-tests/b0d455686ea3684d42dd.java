/*
 * @test
 * @summary Test Comparator.comparingLong with various key extractors
 * @run main java.util.ComparatorWitupTest
 */

import java.util.Comparator;
import java.util.function.ToLongFunction;
import java.io.Serializable;
import java.util.Objects;

public class ComparatorWitupTest {
    public static void main(String[] args) {
        testNullKeyExtractor();
        testComparatorOrdering();
        testComparatorSerializable();
    }

    static void testNullKeyExtractor() {
        try {
            Comparator.comparingLong(null);
            throw new RuntimeException("Expected NullPointerException for null keyExtractor");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testComparatorOrdering() {
        ToLongFunction<String> lengthExtractor = new ToLongFunction<String>() {
            public long applyAsLong(String s) {
                return s.length();
            }
        };

        Comparator<String> comp = Comparator.comparingLong(lengthExtractor);

        // shorter string should be less
        if (comp.compare("a", "abc") >= 0) {
            throw new RuntimeException("Comparator failed: 'a' should be less than 'abc'");
        }

        // equal length strings should compare as 0
        if (comp.compare("abc", "def") != 0) {
            throw new RuntimeException("Comparator failed: 'abc' and 'def' should be equal");
        }

        // longer string should be greater
        if (comp.compare("abcd", "abc") <= 0) {
            throw new RuntimeException("Comparator failed: 'abcd' should be greater than 'abc'");
        }
    }

    static void testComparatorSerializable() {
        ToLongFunction<Integer> identity = new ToLongFunction<Integer>() {
            public long applyAsLong(Integer i) {
                return i.longValue();
            }
        };

        Comparator<Integer> comp = Comparator.comparingLong(identity);

        if (!(comp instanceof Serializable)) {
            throw new RuntimeException("Comparator returned by comparingLong should be Serializable");
        }
    }
}
