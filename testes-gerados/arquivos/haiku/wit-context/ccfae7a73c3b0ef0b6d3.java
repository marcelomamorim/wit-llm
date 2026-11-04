/*
 * @test
 * @summary Verifies BinaryOperator.maxBy returns the greater operand and rejects a null comparator
 * @run main BinaryOperatorWitupTest
 */

import java.util.Comparator;
import java.util.function.BinaryOperator;

public class BinaryOperatorWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Comparator<Integer> natural = Comparator.naturalOrder();
        BinaryOperator<Integer> maxOp = BinaryOperator.maxBy(natural);

        check(maxOp.apply(1, 2) == 2, "maxBy should return the greater value (1,2)");
        check(maxOp.apply(5, 3) == 5, "maxBy should return the greater value (5,3)");
        check(maxOp.apply(4, 4) == 4, "maxBy should return a value when equal");

        BinaryOperator<String> maxStr = BinaryOperator.maxBy(Comparator.<String>naturalOrder());
        check("b".equals(maxStr.apply("a", "b")), "maxBy should work with strings");

        boolean npe = false;
        try {
            BinaryOperator.maxBy(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "maxBy(null) should throw NullPointerException");
    }
}
