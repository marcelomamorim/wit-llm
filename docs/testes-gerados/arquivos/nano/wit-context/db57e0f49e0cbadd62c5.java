/*
 * @test
 * @summary Tests for DoubleStream.iterate method, focusing on null argument handling.
 * @run main DoubleStreamWitupTest
 */

import java.util.stream.DoubleStream;
import java.util.function.DoubleUnaryOperator;

public class DoubleStreamWitupTest {

    public static void main(String[] args) {
        testIterateWithNullFunction();
        testIterateWithValidInputs();
    }

    public static void testIterateWithNullFunction() {
        double seed = 0.0;
        DoubleUnaryOperator f = null;
        try {
            DoubleStream.iterate(seed, f);
            System.err.println("Failed: Expected NullPointerException for null function");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected for null function");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type: " + t);
        }
    }

    public static void testIterateWithValidInputs() {
        double seed = 1.0;
        DoubleUnaryOperator f = x -> x + 1.0;
        DoubleStream stream = DoubleStream.iterate(seed, f);
        double[] result = stream.limit(3).toArray();
        if (result.length == 3 && result[0] == seed && result[1] == seed + 1.0 && result[2] == seed + 2.0) {
            System.out.println("Passed: iterate produces expected sequence");
        } else {
            System.err.println("Failed: iterate sequence mismatch: " + java.util.Arrays.toString(result));
        }
    }
}
