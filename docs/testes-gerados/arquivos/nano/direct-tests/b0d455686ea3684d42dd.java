/* @test    @summary Test for Comparator.comparingLong method    @run main ComparatorWitupTest */

import java.util.Comparator;
import java.util.Objects;
import java.util.function.ToLongFunction;

public class ComparatorWitupTest {

    public static void main(String[] args) {
        testComparingLongWithPositiveNumbers();
        testComparingLongWithNegativeNumbers();
        testComparingLongWithZero();
        testComparingLongWithNullKeyExtractor();
    }

    static void testComparingLongWithPositiveNumbers() {
        ToLongFunction<String> lengthExtractor = String::length;
        Comparator<String> comparator = Comparator.comparingLong(lengthExtractor);
        String s1 = "abc";
        String s2 = "abcd";
        int result = comparator.compare(s1, s2);
        if (result >= 0) {
            throw new AssertionError("Expected s1 < s2 based on length, but got: " + result);
        }
    }

    static void testComparingLongWithNegativeNumbers() {
        ToLongFunction<Integer> valueExtractor = i -> -i;
        Comparator<Integer> comparator = Comparator.comparingLong(valueExtractor);
        Integer i1 = 5;
        Integer i2 = 10;
        int result = comparator.compare(i1, i2);
        if (result >= 0) {
            throw new AssertionError("Expected i1 > i2 based on negative value, but got: " + result);
        }
    }

    static void testComparingLongWithZero() {
        ToLongFunction<Double> valueExtractor = Double::doubleValue;
        Comparator<Double> comparator = Comparator.comparingLong(valueExtractor);
        Double d1 = 0.0;
        Double d2 = -0.0;
        int result = comparator.compare(d1, d2);
        if (result != 0) {
            throw new AssertionError("Expected d1 == d2, but got: " + result);
        }
    }

    static void testComparingLongWithNullKeyExtractor() {
        try {
            Comparator.comparingLong(null);
            throw new AssertionError("Expected NullPointerException for null keyExtractor");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
