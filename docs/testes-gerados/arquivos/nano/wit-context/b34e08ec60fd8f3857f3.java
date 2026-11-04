/*
 * @test
 * @summary Test for ReduceOps.makeRef with null operator to verify NullPointerException
 * @run main ReduceOpsWitupTest
 */

import java.util.function.BinaryOperator;
import java.util.stream.ReduceOps;

public class ReduceOpsWitupTest {

    public static void main(String[] args) {
        testMakeRefWithNullOperator();
    }

    public static void testMakeRefWithNullOperator() {
        try {
            ReduceOps.<Object, Object>makeRef(null);
            System.err.println("Expected NullPointerException was not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception, test passes
        }
    }
}
