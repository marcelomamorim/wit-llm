/*
 * @test
 * @summary Test for ReduceOps.makeInt method, focusing on null argument handling.
 * @run main ReduceOpsWitupTest
 */

import java.util.stream.ReduceOps;
import java.util.function.IntBinaryOperator;

public class ReduceOpsWitupTest {

    public static void main(String[] args) {
        testMakeIntWithNullOperator();
    }

    public static void testMakeIntWithNullOperator() {
        try {
            ReduceOps.makeInt(0, null);
            System.err.println("Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException correctly thrown for null operator.");
        }
    }
}
