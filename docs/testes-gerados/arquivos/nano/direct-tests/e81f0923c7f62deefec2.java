/*
 * @test    @summary Test forEach method of ConcurrentLinkedQueue
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public class ConcurrentLinkedQueueWitupTest {

    public static void main(String[] args) {
        testForEachWithElements();
        testForEachWithEmpty();
    }

    static void testForEachWithElements() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        StringBuilder result = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> result.append(e).append(",");
        queue.forEach(consumer);
        if (!"1,2,3,".equals(result.toString())) {
            throw new AssertionError("Expected '1,2,3,' but got: " + result);
        }
    }

    static void testForEachWithEmpty() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        StringBuilder result = new StringBuilder();
        Consumer<String> consumer = (String e) -> result.append(e).append(",");
        queue.forEach(consumer);
        if (!"".equals(result.toString())) {
            throw new AssertionError("Expected empty string but got: " + result);
        }
    }
}
