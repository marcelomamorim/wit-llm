/* @test    @summary Test for LinkedTransferQueue.forEachRemaining method    @run main LinkedTransferQueueWitupTest */

import java.util.concurrent.LinkedTransferQueue;
import java.util.function.Consumer;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithElements();
        testForEachRemainingEmpty();
    }

    static void testForEachRemainingWithElements() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> sb.append(e);
        queue.forEachRemaining(consumer);
        assert sb.toString().equals("123") : "Expected '123', but got '" + sb.toString() + "'";
    }

    static void testForEachRemainingEmpty() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> sb.append(e);
        queue.forEachRemaining(consumer);
        assert sb.toString().isEmpty() : "Expected empty string, but got '" + sb.toString() + "'";
    }
}
