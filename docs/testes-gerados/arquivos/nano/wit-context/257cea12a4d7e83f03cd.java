/*
 * @test
 * @summary Test for Spliterators.forEachRemaining(IntConsumer)
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;
import java.util.function.IntConsumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullAction();
        testForEachRemainingWithValidAction();
    }

    private static void testForEachRemainingWithNullAction() {
        Spliterators.OfInt spliterator = new Spliterators.OfInt();
        try {
            spliterator.forEachRemaining(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testForEachRemainingWithValidAction() {
        // Using a simple array-backed spliterator for testing
        int[] array = {1, 2, 3};
        Spliterators.OfInt spliterator = new Spliterators.OfInt(array, 0, array.length, 0);
        StringBuilder sb = new StringBuilder();
        IntConsumer consumer = sb::append;
        try {
            spliterator.forEachRemaining(consumer);
            String result = sb.toString();
            if ("123".equals(result)) {
                System.out.println("PASS: forEachRemaining processed elements correctly");
            } else {
                System.out.println("FAIL: Unexpected processing result: " + result);
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
