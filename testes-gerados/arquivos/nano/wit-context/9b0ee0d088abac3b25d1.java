/* @test    @summary Test for BinaryOperator.minBy with null comparator to verify NullPointerException is thrown    @run main BinaryOperatorWitupTest */

import java.util.function.BinaryOperator;
import java.util.Comparator;

public class BinaryOperatorWitupTest {

    public static void main(String[] args) {
        testMinByNullComparator();
    }

    public static void testMinByNullComparator() {
        try {
            BinaryOperator<Object> op = BinaryOperator.minBy(null);
            // If no exception, test fails
            System.err.println("Failed: Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception, test passes
            System.out.println("Passed: NullPointerException thrown as expected.");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type: " + t);
            System.exit(1);
        }
    }
}
