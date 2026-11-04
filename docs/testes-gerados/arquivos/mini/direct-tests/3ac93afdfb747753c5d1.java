/*
 * @test
 * @summary Test Comparator.comparingDouble with various inputs
 * @run main ComparatorWitupTest
 */

import java.util.Comparator;
import java.util.function.ToDoubleFunction;

public class ComparatorWitupTest {
    public static void main(String[] args) {
        testNullKeyExtractor();
        testComparatorOrdering();
        testComparatorWithEqualValues();
        testComparatorWithNegativeAndPositive();
    }

    static void testNullKeyExtractor() {
        try {
            Comparator.comparingDouble(null);
            throw new Error("Expected NullPointerException for null keyExtractor");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testComparatorOrdering() {
        ToDoubleFunction<String> lengthFunc = new ToDoubleFunction<String>() {
            public double applyAsDouble(String s) {
                return s.length();
            }
        };
        Comparator<String> comp = Comparator.comparingDouble(lengthFunc);

        int result1 = comp.compare("a", "abc");
        if (result1 >= 0) {
            throw new Error("Expected 'a' < 'abc' by length");
        }

        int result2 = comp.compare("abcd", "abc");
        if (result2 <= 0) {
            throw new Error("Expected 'abcd' > 'abc' by length");
        }
    }

    static void testComparatorWithEqualValues() {
        ToDoubleFunction<String> zeroFunc = new ToDoubleFunction<String>() {
            public double applyAsDouble(String s) {
                return 0.0;
            }
        };
        Comparator<String> comp = Comparator.comparingDouble(zeroFunc);

        int result = comp.compare("foo", "bar");
        if (result != 0) {
            throw new Error("Expected comparator to return 0 for equal keys");
        }
    }

    static void testComparatorWithNegativeAndPositive() {
        ToDoubleFunction<Integer> identity = new ToDoubleFunction<Integer>() {
            public double applyAsDouble(Integer i) {
                return i.doubleValue();
            }
        };
        Comparator<Integer> comp = Comparator.comparingDouble(identity);

        if (comp.compare(-1, 1) >= 0) {
            throw new Error("Expected -1 < 1");
        }
        if (comp.compare(1, -1) <= 0) {
            throw new Error("Expected 1 > -1");
        }
        if (comp.compare(0, 0) != 0) {
            throw new Error("Expected 0 == 0");
        }
    }
}
