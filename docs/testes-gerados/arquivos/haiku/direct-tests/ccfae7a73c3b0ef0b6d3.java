/*
 * @test
 * @summary Verifies BinaryOperator.maxBy selection, tie handling and null comparator rejection
 * @run main BinaryOperatorWitupTest
 */

import java.util.Comparator;
import java.util.function.BinaryOperator;

public class BinaryOperatorWitupTest {

    public static void main(String[] args) {
        Comparator<String> byLength = (x, y) -> Integer.compare(x.length(), y.length());
        BinaryOperator<String> max = BinaryOperator.maxBy(byLength);

        check("abc", max.apply("abc", "de"));
        check("abc", max.apply("de", "abc"));

        String first = new String("ab");
        String second = new String("cd");
        if (max.apply(first, second) != first) {
            throw new RuntimeException("tie: expected first operand when compare == 0");
        }
        if (max.apply(second, first) != second) {
            throw new RuntimeException("tie: expected first operand when compare == 0 (reversed)");
        }

        try {
            BinaryOperator.maxBy(null);
            throw new RuntimeException("expected NullPointerException for null comparator");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void check(Object expected, Object actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }
}
