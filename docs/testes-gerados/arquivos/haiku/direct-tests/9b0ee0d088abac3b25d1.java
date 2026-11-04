/*
 * @test
 * @summary Behavioral tests for BinaryOperator.minBy(Comparator)
 * @run main BinaryOperatorWitupTest
 */

import java.util.Comparator;
import java.util.function.BinaryOperator;

public class BinaryOperatorWitupTest {

    public static void main(String[] args) {
        testReturnsLesserNatural();
        testReturnsLesserWithReverseOrder();
        testTiePrefersFirstOperand();
        testNullOperandWithZeroComparator();
        testNullComparatorThrowsNPE();
    }

    static void testReturnsLesserNatural() {
        Comparator<Integer> natural = Comparator.naturalOrder();
        BinaryOperator<Integer> op = BinaryOperator.minBy(natural);
        assertEquals(Integer.valueOf(3), op.apply(3, 5));
        assertEquals(Integer.valueOf(3), op.apply(5, 3));
        assertEquals(Integer.valueOf(-2), op.apply(-2, 7));
    }

    static void testReturnsLesserWithReverseOrder() {
        Comparator<Integer> reverse = Comparator.reverseOrder();
        BinaryOperator<Integer> op = BinaryOperator.minBy(reverse);
        assertEquals(Integer.valueOf(5), op.apply(3, 5));
        assertEquals(Integer.valueOf(5), op.apply(5, 3));
    }

    static void testTiePrefersFirstOperand() {
        String a = new String("x");
        String b = new String("x");
        Comparator<String> zero = (x, y) -> 0;
        BinaryOperator<String> op = BinaryOperator.minBy(zero);
        if (op.apply(a, b) != a) {
            throw new RuntimeException("tie should return first operand");
        }
        if (op.apply(b, a) != b) {
            throw new RuntimeException("tie should return first operand (swapped)");
        }
    }

    static void testNullOperandWithZeroComparator() {
        Comparator<String> zero = (x, y) -> 0;
        BinaryOperator<String> op = BinaryOperator.minBy(zero);
        if (op.apply(null, "b") != null) {
            throw new RuntimeException("expected null first operand to be returned");
        }
    }

    static void testNullComparatorThrowsNPE() {
        try {
            BinaryOperator.minBy(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null comparator");
    }

    static void assertEquals(Object expected, Object actual) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }
}
