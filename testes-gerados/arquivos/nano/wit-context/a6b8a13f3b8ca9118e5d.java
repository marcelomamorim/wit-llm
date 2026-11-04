/*
 * @test
 * @summary Test for LongStream.iterate method to verify behavior and exception handling.
 * @run main LongStreamWitupTest
 */

import java.util.stream.LongStream;
import java.util.function.LongUnaryOperator;

public class LongStreamWitupTest {

    public static void main(String[] args) {
        testIterateWithNullFunction();
        testIterateWithValidParameters();
    }

    private static void testIterateWithNullFunction() {
        boolean exceptionThrown = false;
        try {
            LongStream.iterate(0L, null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null function");
        }
    }

    private static void testIterateWithValidParameters() {
        LongUnaryOperator f = x -> x + 2;
        LongStream stream = LongStream.iterate(10L, f);
        long[] result = stream.limit(3).toArray();
        long[] expected = {10L, 12L, 14L};
        if (!java.util.Arrays.equals(result, expected)) {
            throw new AssertionError("Unexpected stream output: " + java.util.Arrays.toString(result));
        }
    }
}
