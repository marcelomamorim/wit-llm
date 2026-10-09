/*
 * @test
 * @summary Test LinkedBlockingDeque.putLast(E) for null input and normal insertion
 * @run main LinkedBlockingDequeWitupTest_Pd367bb432f99
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class LinkedBlockingDequeWitupTest_Pd367bb432f99 {
    public static void main(String[] args) throws Exception {
        testPutLastNull();
        testPutLastNormal();
    }

    static void testPutLastNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.putLast(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        } catch (InterruptedException ie) {
            throw new Error("Unexpected InterruptedException", ie);
        }
    }

    static void testPutLastNormal() throws InterruptedException {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(1);
        deque.putLast("test");
        // The deque should contain the element just inserted
        String polled = deque.pollLast(1, TimeUnit.SECONDS);
        if (!"test".equals(polled)) {
            throw new Error("Element inserted and polled do not match");
        }
    }
}
