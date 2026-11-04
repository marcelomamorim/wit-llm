/* @test    @summary Test for BinaryOperator.minBy method to ensure it returns the lesser element according to the comparator.    @run main BinaryOperatorWitupTest */

import java.util.Comparator;
import java.util.function.BinaryOperator;

public class BinaryOperatorWitupTest {

    public static void main(String[] args) {
        testMinByWithIntegers();
        testMinByWithStrings();
        testMinByWithNullComparator();
    }

    static void testMinByWithIntegers() {
        Comparator<Integer> comparator = Integer::compare;
        BinaryOperator<Integer> minBy = BinaryOperator.minBy(comparator);
        int result = minBy.apply(10, 20);
        if (result != 10) {
            throw new AssertionError("Expected 10, but got " + result);
        }
    }

    static void testMinByWithStrings() {
        Comparator<String> comparator = String::compareTo;
        BinaryOperator<String> minBy = BinaryOperator.minBy(comparator);
        String result = minBy.apply("banana", "apple");
        if (!"apple".equals(result)) {
            throw new AssertionError("Expected 'apple', but got '" + result + "'");
        }
    }

    static void testMinByWithNullComparator() {
        try {
            BinaryOperator.minBy(null);
            throw new AssertionError("Expected NullPointerException for null comparator");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
