/*
 * @test
 * @summary Deterministic tests for LinkedBlockingDeque.putFirst(E) method
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        testPutFirstNullElement();
        testPutFirstNormalElement();
    }

    static void testPutFirstNullElement() {
        LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>();
        try {
            deque.putFirst(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (InterruptedException ie) {
            throw new Error("Unexpected InterruptedException", ie);
        }
    }

    static void testPutFirstNormalElement() throws InterruptedException {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        String element = "test";
        deque.putFirst(element);
        // Verify element is at first position by polling first
        String polled = deque.pollFirst();
        if (!element.equals(polled)) {
            throw new Error("Element putFirst not found at first position");
        }
    }
}
