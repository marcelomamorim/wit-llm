/*
 * @test
 * @summary Testa o método Arrays.parallelPrefix com entrada nula para o parâmetro BinaryOperator.
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.function.BinaryOperator;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testParallelPrefixWithNullOperator();
        testParallelPrefixWithEmptyArray();
        testParallelPrefixWithValidInput();
    }

    public static void testParallelPrefixWithNullOperator() {
        int[] array = {1, 2, 3};
        try {
            Arrays.parallelPrefix(array, null);
            System.out.println("FAIL: Expected NullPointerException for null operator");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null operator");
        }
    }

    public static void testParallelPrefixWithEmptyArray() {
        Integer[] array = new Integer[0];
        try {
            Arrays.parallelPrefix(array, (a, b) -> a);
            System.out.println("PASS: No exception for empty array");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for empty array: " + e);
        }
    }

    public static void testParallelPrefixWithValidInput() {
        Integer[] array = {1, 2, 3, 4};
        BinaryOperator<Integer> sumOp = (a, b) -> a + b;
        try {
            Arrays.parallelPrefix(array, sumOp);
            if (Arrays.equals(array, new Integer[]{1, 3, 6, 10})) {
                System.out.println("PASS: parallelPrefix computed correct prefix sum");
            } else {
                System.out.println("FAIL: parallelPrefix result incorrect: " + java.util.Arrays.toString(array));
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception during valid input: " + e);
        }
    }
}
