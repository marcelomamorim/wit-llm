/* @test    @summary Test Arrays.setAll with various inputs    @run main ArraysWitupTest */

import java.util.Arrays;
import java.util.function.IntToDoubleFunction;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testSetAllWithPositiveIndices();
        testSetAllWithEmptyArray();
        testSetAllWithNullGenerator();
    }

    static void testSetAllWithPositiveIndices() {
        double[] array = new double[5];
        Arrays.setAll(array, i -> i * 1.0);
        boolean success = true;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != i * 1.0) {
                success = false;
                break;
            }
        }
        if (!success) {
            throw new AssertionError("Failed: setAll did not assign expected values for positive indices");
        }
    }

    static void testSetAllWithEmptyArray() {
        double[] array = new double[0];
        Arrays.setAll(array, i -> 42.0);
        if (array.length != 0) {
            throw new AssertionError("Failed: setAll altered empty array");
        }
    }

    static void testSetAllWithNullGenerator() {
        double[] array = {1.0, 2.0};
        boolean exceptionThrown = false;
        try {
            Arrays.setAll(array, null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Failed: setAll did not throw NullPointerException for null generator");
        }
    }
}
