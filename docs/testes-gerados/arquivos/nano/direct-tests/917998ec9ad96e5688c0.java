/*
 * @test    @summary Test remove(Object o) method of LinkedBlockingQueue
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.NoSuchElementException;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRemoveExistingElement();
        testRemoveNonExistingElement();
        testRemoveNull();
    }

    static void testRemoveExistingElement() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");
        boolean removed = queue.remove("b");
        assert removed : "Element 'b' should be removed";
        assert queue.size() == 2 : "Queue size should be 2 after removal";
        assert !queue.contains("b") : "Queue should not contain 'b'";
    }

    static void testRemoveNonExistingElement() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("x");
        queue.add("y");
        boolean removed = queue.remove("z");
        assert !removed : "Removing non-existing element should return false";
        assert queue.size() == 2 : "Queue size should remain 2";
    }

    static void testRemoveNull() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("test");
        boolean removed = queue.remove(null);
        assert !removed : "Removing null should return false";
        assert queue.size() == 1 : "Queue size should remain 1";
    }
}
