/*
 * @test
 * @summary Tests BinaryOperator.minBy null comparator check and selection behavior
 * @run main BinaryOperatorWitupTest
 */
import java.util.Comparator;
import java.util.function.BinaryOperator;

public class BinaryOperatorWitupTest {
    public static void main(String[] args) {
        testNullComparatorThrowsNPE();
        testReturnsLesserOperand();
        testTieReturnsFirstOperand();
    }

    static void testNullComparatorThrowsNPE() {
        try {
            BinaryOperator.minBy(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null comparator");
    }

    static void testReturnsLesserOperand() {
        BinaryOperator<Integer> op = BinaryOperator.minBy(Comparator.naturalOrder());
        check(Integer.valueOf(3).equals(op.apply(3, 7)), "minBy(3,7) should be 3");
        check(Integer.valueOf(3).equals(op.apply(7, 3)), "minBy(7,3) should be 3");
    }

    static void testTieReturnsFirstOperand() {
        Comparator<String> byLength = Comparator.comparingInt(String::length);
        BinaryOperator<String> op = BinaryOperator.minBy(byLength);
        check("ab".equals(op.apply("ab", "cd")), "tie should return first operand");
        check("cd".equals(op.apply("cd", "ab")), "tie should return first operand");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
