/*
 * @test
 * @summary Test for ArrayBlockingQueue's forEachRemaining method to verify exception paths and behavior.
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Consumer;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingNormal();
    }

    static void testForEachRemainingNullAction() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        Consumer<? super Integer> nullConsumer = null;
        try {
            queue.forEachRemaining(nullConsumer);
            System.out.println("FAIL: Expected NullPointerException for null action");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null action");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    static void testForEachRemainingNormal() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        queue.add(1);
        queue.add(2);
        queue.add(3);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> sb.append(e).append(",");
        try {
            queue.forEachRemaining(consumer);
            String result = sb.toString();
            if (result.startsWith("1,2,3")) {
                System.out.println("PASS: forEachRemaining processed elements correctly");
            } else {
                System.out.println("FAIL: Unexpected processing result: " + result);
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception during normal processing: " + t);
        }
    }
}
