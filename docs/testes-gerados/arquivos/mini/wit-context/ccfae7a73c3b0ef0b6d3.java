/*
 * @test
 * @summary Test BinaryOperator.maxBy static method behavior and null check
 * @run main BinaryOperatorWitupTest
 */

import java.util.function.BinaryOperator;
import java.util.Comparator;
import java.util.Objects;

public class BinaryOperatorWitupTest {
    public static void main(String[] args) {
        testMaxByWithValidComparator();
        testMaxByWithNullComparator();
    }

    private static void testMaxByWithValidComparator() {
        Comparator<Integer> comp = new Comparator<Integer>() {
            public int compare(Integer a, Integer b) {
                return a.compareTo(b);
            }
        };

        BinaryOperator<Integer> maxOp = BinaryOperator.maxBy(comp);

        Integer result1 = maxOp.apply(5, 10);
        if (!result1.equals(10)) {
            throw new AssertionError("Expected max of 5 and 10 to be 10, got " + result1);
        }

        Integer result2 = maxOp.apply(20, 15);
        if (!result2.equals(20)) {
            throw new AssertionError("Expected max of 20 and 15 to be 20, got " + result2);
        }

        Integer result3 = maxOp.apply(7, 7);
        if (!result3.equals(7)) {
            throw new AssertionError("Expected max of 7 and 7 to be 7, got " + result3);
        }
    }

    private static void testMaxByWithNullComparator() {
        try {
            BinaryOperator.maxBy(null);
            throw new AssertionError("Expected NullPointerException when comparator is null");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
