/*
 * @test
 * @summary Tests for LinkedBlockingQueue behavior, including exception paths.
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullAction();
        testForEachRemainingWithEmptyQueue();
        testForEachRemainingNormal();
    }

    public static void testForEachRemainingWithNullAction() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        try {
            queue.forEachRemaining(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    public static void testForEachRemainingWithEmptyQueue() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        try {
            queue.forEachRemaining(e -> {
                System.out.println("This should not be called");
            });
            System.out.println("PASS: No exception on empty queue");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    public static void testForEachRemainingNormal() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("a");
        queue.add("b");
        StringBuilder sb = new StringBuilder();
        try {
            queue.forEachRemaining(e -> sb.append(e));
            if (sb.toString().equals("ab")) {
                System.out.println("PASS: forEachRemaining processed elements correctly");
            } else {
                System.out.println("FAIL: Unexpected processing result: " + sb.toString());
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
