/* @test    @summary Test size() method of PriorityBlockingQueue    @run main PriorityBlockingQueueWitupTest */

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testEmptyQueueSize();
        testNonEmptyQueueSize();
    }

    static void testEmptyQueueSize() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        int size = queue.size();
        if (size != 0) {
            throw new AssertionError("Expected size 0, but got " + size);
        }
    }

    static void testNonEmptyQueueSize() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        queue.add("a");
        queue.add("b");
        int size = queue.size();
        if (size != 2) {
            throw new AssertionError("Expected size 2, but got " + size);
        }
    }
}
