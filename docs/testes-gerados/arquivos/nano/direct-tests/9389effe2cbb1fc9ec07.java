/*
 * @test
 * @summary Test constructor of LinkedBlockingQueue with a collection parameter.
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testConstructorWithCollection();
        testConstructorWithEmptyCollection();
        testConstructorWithNullElement();
        testConstructorWithNullCollection();
    }

    static void testConstructorWithCollection() {
        java.util.List<String> list = Arrays.asList("a", "b", "c");
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(list);
        assert queue.size() == 3 : "Queue size should be 3";
        assert queue.peek().equals("a") : "First element should be 'a'";
    }

    static void testConstructorWithEmptyCollection() {
        java.util.List<String> emptyList = Arrays.asList();
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(emptyList);
        assert queue.isEmpty() : "Queue should be empty";
    }

    static void testConstructorWithNullElement() {
        java.util.List<String> listWithNull = Arrays.asList("a", null, "c");
        try {
            new LinkedBlockingQueue<>(listWithNull);
            assert false : "Should throw NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testConstructorWithNullCollection() {
        try {
            new LinkedBlockingQueue<>(null);
            assert false : "Should throw NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
