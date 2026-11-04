/*
 * @test
 * @summary Test BinaryOperator.minBy static method behavior
 * @run main BinaryOperatorWitupTest
 */

import java.util.function.BinaryOperator;
import java.util.Comparator;
import java.util.Objects;

public class BinaryOperatorWitupTest {
    public static void main(String[] args) {
        testMinByReturnsLesserElement();
        testMinByWithNullComparatorThrowsNPE();
    }

    private static void testMinByReturnsLesserElement() {
        Comparator<Integer> cmp = new Comparator<Integer>() {
            public int compare(Integer a, Integer b) {
                return a - b;
            }
        };

        BinaryOperator<Integer> minOp = BinaryOperator.minBy(cmp);

        Integer result1 = minOp.apply(5, 10);
        if (!Objects.equals(result1, 5)) {
            throw new AssertionError("Expected 5 as min of 5 and 10, got " + result1);
        }

        Integer result2 = minOp.apply(10, 5);
        if (!Objects.equals(result2, 5)) {
            throw new AssertionError("Expected 5 as min of 10 and 5, got " + result2);
        }

        Integer result3 = minOp.apply(7, 7);
        if (!Objects.equals(result3, 7)) {
            throw new AssertionError("Expected 7 as min of 7 and 7, got " + result3);
        }
    }

    private static void testMinByWithNullComparatorThrowsNPE() {
        try {
            BinaryOperator.minBy(null);
            throw new AssertionError("Expected NullPointerException for null comparator");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
