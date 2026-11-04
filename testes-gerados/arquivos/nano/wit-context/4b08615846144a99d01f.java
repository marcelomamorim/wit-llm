/*
 * @test    @summary Test for Arrays.setAll with null generator to verify NullPointerException is thrown.
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.function.IntUnaryOperator;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testSetAllWithNullGenerator();
    }

    public static void testSetAllWithNullGenerator() {
        int[] array = {1, 2, 3};
        try {
            Arrays.setAll(array, null);
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        }
    }
}
