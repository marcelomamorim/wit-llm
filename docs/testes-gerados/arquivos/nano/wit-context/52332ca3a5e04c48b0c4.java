/*
 * @test
 * @summary Tests for IntStream.iterate method to verify behavior and exception handling.
 * @run main IntStreamWitupTest
 */

import java.util.stream.IntStream;
import java.util.function.IntUnaryOperator;

public class IntStreamWitupTest {

    public static void main(String[] args) {
        testIterateWithNullFunction();
        testIterateWithValidFunction();
    }

    public static void testIterateWithNullFunction() {
        boolean exceptionThrown = false;
        try {
            IntStream.iterate(0, null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to iterate");
        }
    }

    public static void testIterateWithValidFunction() {
        IntUnaryOperator plusOne = x -> x + 1;
        IntStream stream = IntStream.iterate(5, plusOne);
        int[] result = new int[3];
        int index = 0;
        stream.limit(3).forEach(i -> {
            result[index++] = i;
        });
        if (result.length != 3 || result[0] != 5 || result[1] != 6 || result[2] != 7) {
            throw new AssertionError("Unexpected stream output: " + java.util.Arrays.toString(result));
        }
    }
}
