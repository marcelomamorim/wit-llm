/* @test    @summary Test add(E e) method of LinkedBlockingDeque for null and non-null inputs    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testAddWithNull();
        testAddWithValidElement();
    }

    private static void testAddWithNull() {
        LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.add(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when adding null");
        }
    }

    private static void testAddWithValidElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean result = false;
        try {
            result = deque.add("element");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception when adding valid element", e);
        }
        if (!result) {
            throw new AssertionError("add() should return true for valid element");
        }
        if (deque.isEmpty()) {
            throw new AssertionError("Deque should contain the added element");
        }
    }
}
