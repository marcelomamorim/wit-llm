/*
 * @test
 * @summary Test for Arrays.setAll with null generator to verify NullPointerException
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.function.IntToLongFunction;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testSetAllWithNullGenerator();
    }

    public static void testSetAllWithNullGenerator() {
        long[] array = {1L, 2L, 3L};
        try {
            Arrays.setAll(array, null);
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        }
    }
}
