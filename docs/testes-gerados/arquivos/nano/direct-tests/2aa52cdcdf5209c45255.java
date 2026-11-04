/* @test    @summary Test getFirst() method of LinkedBlockingDeque for normal and empty cases    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testGetFirstWithElements();
        testGetFirstEmptyDeque();
    }

    static void testGetFirstWithElements() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("element1");
        deque.add("element2");
        String result = deque.getFirst();
        if (!"element1".equals(result)) {
            throw new AssertionError("Expected 'element1', but got: " + result);
        }
    }

    static void testGetFirstEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.getFirst();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NoSuchElementException to be thrown");
        }
    }
}
