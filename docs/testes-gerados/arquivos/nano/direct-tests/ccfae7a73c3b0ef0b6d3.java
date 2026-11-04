/* @test    @summary Test for BinaryOperator.maxBy method with various comparators    @run main BinaryOperatorWitupTest */

import java.util.Comparator;
import java.util.function.BinaryOperator;

public class BinaryOperatorWitupTest {

    public static void main(String[] args) {
        testMaxByWithIntegerComparator();
        testMaxByWithStringComparator();
        testMaxByWithNullComparator();
    }

    private static void testMaxByWithIntegerComparator() {
        Comparator<Integer> comparator = Integer::compare;
        BinaryOperator<Integer> maxBy = BinaryOperator.maxBy(comparator);
        int result = maxBy.apply(10, 20);
        if (result != 20) {
            throw new AssertionError("Expected 20, but got " + result);
        }
    }

    private static void testMaxByWithStringComparator() {
        Comparator<String> comparator = String::compareTo;
        BinaryOperator<String> maxBy = BinaryOperator.maxBy(comparator);
        String result = maxBy.apply("apple", "banana");
        if (!"banana".equals(result)) {
            throw new AssertionError("Expected 'banana', but got " + result);
        }
    }

    private static void testMaxByWithNullComparator() {
        try {
            BinaryOperator.maxBy(null);
            throw new AssertionError("Expected NullPointerException to be thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
