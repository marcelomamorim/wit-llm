/*
 * @test
 * @summary Test the forEach method of LinkedTransferQueue for correct iteration and null handling
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.function.Consumer;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testForEachWithElements();
        testForEachWithNullAction();
        testForEachWithEmptyQueue();
    }

    static void testForEachWithElements() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> sb.append(e);
        queue.forEach(consumer);
        assert sb.toString().equals("123") : "Expected concatenation of elements to be '123', but was '" + sb.toString() + "'";
    }

    static void testForEachWithNullAction() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        queue.add(1);
        try {
            queue.forEach(null);
            assert false : "Expected NullPointerException when passing null to forEach";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testForEachWithEmptyQueue() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> sb.append(e);
        queue.forEach(consumer); // should not throw
        assert sb.length() == 0 : "Expected no elements to be processed";
    }
}
