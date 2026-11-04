/*
 * @test
 * @summary Testa o método Arrays.parallelSetAll com entrada nula para o gerador.
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.function.IntToLongFunction;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testParallelSetAll_NullGenerator();
        testParallelSetAll_NormalCase();
    }

    public static void testParallelSetAll_NullGenerator() {
        long[] array = {1L, 2L, 3L};
        try {
            Arrays.parallelSetAll(array, null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    public static void testParallelSetAll_NormalCase() {
        long[] array = new long[5];
        IntToLongFunction generator = i -> i * 10L;
        Arrays.parallelSetAll(array, generator);
        boolean success = true;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != i * 10L) {
                success = false;
                break;
            }
        }
        if (success) {
            System.out.println("PASS: Array correctly populated");
        } else {
            System.out.println("FAIL: Array not populated as expected");
        }
    }
}
