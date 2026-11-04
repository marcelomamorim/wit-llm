/* @test    @summary Test the add method of LinkedBlockingDeque for basic functionality and null handling    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testAddWithElement();
        testAddNullElement();
    }

    static void testAddWithElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean result = false;
        try {
            result = deque.add("element");
        } catch (Exception e) {
            throw new AssertionError("add should not throw exception for non-null element", e);
        }
        if (!result) {
            throw new AssertionError("add should return true");
        }
        if (deque.isEmpty()) {
            throw new AssertionError("Deque should contain the added element");
        }
        String first = deque.peekFirst();
        if (!"element".equals(first)) {
            throw new AssertionError("First element should be 'element', but was: " + first);
        }
    }

    static void testAddNullElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.add(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        } catch (Exception e) {
            throw new AssertionError("add(null) should throw NullPointerException", e);
        }
        if (!exceptionThrown) {
            throw new AssertionError("add(null) did not throw NullPointerException");
        }
        if (!deque.isEmpty()) {
            throw new AssertionError("Deque should be empty after failed add(null)");
        }
    }
}
