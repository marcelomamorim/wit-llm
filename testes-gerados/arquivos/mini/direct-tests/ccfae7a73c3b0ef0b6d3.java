/*
 * @test
 * @summary Test BinaryOperator.maxBy static method behavior and null handling
 * @run main BinaryOperatorWitupTest
 */

import java.util.function.BinaryOperator;
import java.util.Comparator;
import java.util.Objects;

public class BinaryOperatorWitupTest {
    public static void main(String[] args) {
        testMaxByWithNaturalOrder();
        testMaxByWithReverseOrder();
        testMaxByWithEqualElements();
        testMaxByNullComparator();
    }

    static void testMaxByWithNaturalOrder() {
        BinaryOperator<Integer> maxOp = BinaryOperator.maxBy(Comparator.naturalOrder());
        Integer result = maxOp.apply(5, 10);
        if (!result.equals(10)) {
            throw new AssertionError("Expected 10 but got " + result);
        }
    }

    static void testMaxByWithReverseOrder() {
        BinaryOperator<Integer> maxOp = BinaryOperator.maxBy(Comparator.reverseOrder());
        Integer result = maxOp.apply(5, 10);
        if (!result.equals(5)) {
            throw new AssertionError("Expected 5 but got " + result);
        }
    }

    static void testMaxByWithEqualElements() {
        BinaryOperator<String> maxOp = BinaryOperator.maxBy(String::compareTo);
        String a = "abc";
        String b = "abc";
        String result = maxOp.apply(a, b);
        if (!result.equals(a)) {
            throw new AssertionError("Expected first argument when equal but got " + result);
        }
    }

    static void testMaxByNullComparator() {
        try {
            BinaryOperator.maxBy(null);
            throw new AssertionError("Expected NullPointerException for null comparator");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
