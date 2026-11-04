/* @test    @summary Test PriorityBlockingQueue.toArray() method for correctness and thread safety    @run main PriorityBlockingQueueWitupTest */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.Arrays;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testEmptyQueue();
        testSingleElement();
        testMultipleElements();
        testConcurrentAccess();
    }

    static void testEmptyQueue() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        Object[] array = queue.toArray();
        assert array != null : "Array should not be null";
        assert array.length == 0 : "Array length should be 0";
    }

    static void testSingleElement() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        queue.add("element");
        Object[] array = queue.toArray();
        assert array.length == 1 : "Array length should be 1";
        assert array[0].equals("element") : "Element should be 'element'";
    }

    static void testMultipleElements() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(5);
        queue.add(1);
        queue.add(3);
        Object[] array = queue.toArray();
        assert array.length == 3 : "Array length should be 3";
        Integer[] intArray = Arrays.copyOf(array, array.length, Integer[].class);
        Arrays.sort(intArray);
        assert intArray[0] == 1 : "First element should be 1";
        assert intArray[1] == 3 : "Second element should be 3";
        assert intArray[2] == 5 : "Third element should be 5";
    }

    static void testConcurrentAccess() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                queue.add(i);
            }
        });
        Thread consumer = new Thread(() -> {
            try {
                Thread.sleep(50);
                Object[] array = queue.toArray();
                assert array != null : "Array should not be null during concurrent access";
                assert array.length <= 10 : "Array length should be at most 10";
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        producer.start();
        consumer.start();
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
