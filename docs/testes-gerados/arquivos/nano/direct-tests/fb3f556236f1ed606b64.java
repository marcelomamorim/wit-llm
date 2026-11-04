/*
 * @test    @summary Test for LinkedBlockingQueue's forEachRemaining method
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithElements();
        testForEachRemainingEmpty();
        testForEachRemainingNullAction();
    }

    static void testForEachRemainingWithElements() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> sb.append(e).append(",");
        queue.forEachRemaining(consumer);
        String result = sb.toString();
        if (!"1,2,3,".equals(result)) {
            throw new AssertionError("Expected '1,2,3,', but got: '" + result + "'");
        }
    }

    static void testForEachRemainingEmpty() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> sb.append(e).append(",");
        queue.forEachRemaining(consumer); // should do nothing
        if (sb.length() != 0) {
            throw new AssertionError("Expected empty StringBuilder, but got: '" + sb.toString() + "'");
        }
    }

    static void testForEachRemainingNullAction() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.add(1);
        try {
            queue.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
