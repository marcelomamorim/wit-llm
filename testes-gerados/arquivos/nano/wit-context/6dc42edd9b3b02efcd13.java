/*
 * @test
 * @summary Test for Spliterators.forEachRemaining(LongConsumer) method, focusing on null input handling.
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;
import java.util.function.LongConsumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullAction();
        testForEachRemainingWithValidAction();
    }

    private static void testForEachRemainingWithNullAction() {
        Spliterators.LongArraySpliterator spliterator = new Spliterators.LongArraySpliterator(new long[]{1, 2, 3});
        try {
            spliterator.forEachRemaining(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testForEachRemainingWithValidAction() {
        long[] data = {10, 20, 30};
        Spliterators.LongArraySpliterator spliterator = new Spliterators.LongArraySpliterator(data);
        StringBuilder sb = new StringBuilder();
        LongConsumer consumer = new LongConsumer() {
            @Override
            public void accept(long value) {
                sb.append(value).append(",");
            }
        };
        spliterator.forEachRemaining(consumer);
        String result = sb.toString();
        if ("10,20,30,".equals(result)) {
            System.out.println("PASS: forEachRemaining processed all elements correctly");
        } else {
            System.out.println("FAIL: Unexpected processing result: " + result);
        }
    }
}
