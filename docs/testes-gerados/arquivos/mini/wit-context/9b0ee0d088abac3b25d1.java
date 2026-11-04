/*
 * @test
 * @summary Test BinaryOperator.minBy static method behavior and null handling
 * @run main BinaryOperatorWitupTest
 */

import java.util.function.BinaryOperator;
import java.util.Comparator;
import java.util.Objects;

public class BinaryOperatorWitupTest {
    public static void main(String[] args) {
        testMinByWithValidComparator();
        testMinByWithNullComparator();
    }

    private static void testMinByWithValidComparator() {
        Comparator<Integer> comp = new Comparator<Integer>() {
            public int compare(Integer a, Integer b) {
                return a - b;
            }
        };

        BinaryOperator<Integer> op = BinaryOperator.minBy(comp);

        Integer result1 = op.apply(1, 2);
        if (!result1.equals(1)) {
            throw new AssertionError("Expected minBy to return lesser element 1 but got " + result1);
        }

        Integer result2 = op.apply(2, 1);
        if (!result2.equals(1)) {
            throw new AssertionError("Expected minBy to return lesser element 1 but got " + result2);
        }

        Integer result3 = op.apply(5, 5);
        if (!result3.equals(5)) {
            throw new AssertionError("Expected minBy to return equal element 5 but got " + result3);
        }
    }

    private static void testMinByWithNullComparator() {
        try {
            BinaryOperator.minBy(null);
            throw new AssertionError("Expected NullPointerException when comparator is null");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
