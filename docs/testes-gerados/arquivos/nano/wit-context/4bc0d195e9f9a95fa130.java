/*
 * @test
 * @summary Test for Arrays.setAll with null generator to verify NullPointerException
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.function.IntToDoubleFunction;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testSetAllWithNullGenerator();
    }

    public static void testSetAllWithNullGenerator() {
        double[] array = {1.0, 2.0, 3.0};
        try {
            Arrays.setAll(array, null);
            // If no exception, the test fails
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        }
    }
}
