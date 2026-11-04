/*
 * @test
 * @summary Test for Arrays.replaceAll with null operator
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.function.UnaryOperator;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testReplaceAllWithNullOperator();
        testReplaceAllWithValidOperator();
    }

    static void testReplaceAllWithNullOperator() {
        int[] array = {1, 2, 3};
        try {
            Arrays.replaceAll(array, null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    static void testReplaceAllWithValidOperator() {
        Integer[] array = {1, 2, 3};
        UnaryOperator<Integer> operator = x -> x * 2;
        try {
            Arrays.replaceAll(array, operator);
            boolean result = array[0] == 2 && array[1] == 4 && array[2] == 6;
            if (result) {
                System.out.println("PASS: replaceAll applied correctly");
            } else {
                System.out.println("FAIL: replaceAll did not modify array as expected");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
