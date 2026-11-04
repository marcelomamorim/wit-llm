/*
 * @test
 * @summary Test for LinkedBlockingDeque.remove(Object o)
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testRemoveWithNull();
        testRemoveWithNonExistingElement();
        testRemoveWithExistingElement();
    }

    private static void testRemoveWithNull() {
        LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>();
        boolean result = deque.remove(null);
        System.out.println("remove(null): " + result);
    }

    private static void testRemoveWithNonExistingElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("a");
        boolean result = deque.remove("b");
        System.out.println("remove("b"): " + result);
    }

    private static void testRemoveWithExistingElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("a");
        deque.add("b");
        boolean result = deque.remove("a");
        System.out.println("remove("a"): " + result);
        System.out.println("Deque after removal: " + deque);
    }
}
