/* @test    @summary Test for Comparator.comparingDouble method    @run main ComparatorWitupTest */

import java.util.Comparator;
import java.util.Objects;
import java.util.function.ToDoubleFunction;

public class ComparatorWitupTest {

    public static void main(String[] args) {
        testComparingDoubleWithPositiveValues();
        testComparingDoubleWithNegativeValues();
        testComparingDoubleWithEqualValues();
        testComparingDoubleWithNullKeyExtractor();
    }

    static void testComparingDoubleWithPositiveValues() {
        ToDoubleFunction<String> lengthExtractor = String::length;
        Comparator<String> comparator = Comparator.comparingDouble(lengthExtractor);
        String s1 = "abc"; // length 3
        String s2 = "abcd"; // length 4
        int result = comparator.compare(s1, s2);
        assert result < 0 : "Expected s1 < s2, got " + result;
    }

    static void testComparingDoubleWithNegativeValues() {
        ToDoubleFunction<Integer> valueExtractor = i -> -i;
        Comparator<Integer> comparator = Comparator.comparingDouble(valueExtractor);
        Integer i1 = 10;
        Integer i2 = 5;
        int result = comparator.compare(i1, i2); // compare -10 and -5
        assert result > 0 : "Expected -10 > -5, got " + result;
    }

    static void testComparingDoubleWithEqualValues() {
        ToDoubleFunction<Double> valueExtractor = d -> d;
        Comparator<Double> comparator = Comparator.comparingDouble(valueExtractor);
        Double d1 = 3.14;
        Double d2 = 3.14;
        int result = comparator.compare(d1, d2);
        assert result == 0 : "Expected equal, got " + result;
    }

    static void testComparingDoubleWithNullKeyExtractor() {
        try {
            Comparator.comparingDouble(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
