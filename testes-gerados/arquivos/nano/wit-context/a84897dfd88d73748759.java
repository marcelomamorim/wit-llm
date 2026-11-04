/*
 * @test
 * @summary Test for Arrays.parallelSetAll handling null generator
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.function.IntUnaryOperator;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testParallelSetAllWithNullGenerator();
        testParallelSetAllWithValidGenerator();
    }

    public static void testParallelSetAllWithNullGenerator() {
        int[] array = {1, 2, 3};
        try {
            Arrays.parallelSetAll(array, null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    public static void testParallelSetAllWithValidGenerator() {
        int[] array = {0, 0, 0};
        IntUnaryOperator generator = i -> i * 10;
        Arrays.parallelSetAll(array, generator);
        if (array.length == 3 && array[0] == 0 && array[1] == 10 && array[2] == 20) {
            System.out.println("PASS: Array correctly populated by generator");
        } else {
            System.out.println("FAIL: Array not populated as expected: " + java.util.Arrays.toString(array));
        }
    }
}
