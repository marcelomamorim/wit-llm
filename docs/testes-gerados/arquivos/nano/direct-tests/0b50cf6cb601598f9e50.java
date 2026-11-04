/*
 * @test    @summary Test for ArrayBlockingQueue.forEach method
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Consumer;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testForEachWithElements();
        testForEachWithEmptyQueue();
        testForEachNullAction();
    }

    static void testForEachWithElements() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);
        queue.add(1);
        queue.add(2);
        queue.add(3);
        StringBuilder result = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> result.append(e).append(",");
        queue.forEach(consumer);
        String expected = "1,2,3,";
        if (!result.toString().equals(expected)) {
            throw new AssertionError("Expected: " + expected + " but got: " + result);
        }
    }

    static void testForEachWithEmptyQueue() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
        StringBuilder result = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> result.append(e).append(",");
        queue.forEach(consumer);
        String expected = "";
        if (!result.toString().equals(expected)) {
            throw new AssertionError("Expected empty string but got: " + result);
        }
    }

    static void testForEachNullAction() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);
        boolean exceptionThrown = false;
        try {
            queue.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException to be thrown") ;
        }
    }
}
