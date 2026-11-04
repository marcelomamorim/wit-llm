/* @test    @summary Test for BinaryOperator.maxBy with null comparator    @run main BinaryOperatorWitupTest */

import java.util.Comparator;
import java.util.function.BinaryOperator;

public class BinaryOperatorWitupTest {

    public static void main(String[] args) {
        testMaxByWithNullComparator();
    }

    public static void testMaxByWithNullComparator() {
        try {
            BinaryOperator.maxBy(null);
            System.err.println("Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception, test passes
            System.out.println("NullPointerException correctly thrown.");
        }
    }
}
