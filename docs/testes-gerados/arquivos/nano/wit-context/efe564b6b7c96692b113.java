/*
 * @test
 * @summary Testa o método Arrays.parallelSetAll para entradas nulas.
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.function.IntFunction;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testParallelSetAllWithNullGenerator();
        testParallelSetAllWithValidGenerator();
    }

    private static void testParallelSetAllWithNullGenerator() {
        Object[] array = new Object[5];
        try {
            Arrays.parallelSetAll(array, null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testParallelSetAllWithValidGenerator() {
        Integer[] array = new Integer[3];
        IntFunction<Integer> generator = i -> i * 10;
        try {
            Arrays.parallelSetAll(array, generator);
            if (array[0] == 0 && array[1] == 10 && array[2] == 20) {
                System.out.println("PASS: Array correctly populated");
            } else {
                System.out.println("FAIL: Array not populated as expected: " + java.util.Arrays.toString(array));
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
