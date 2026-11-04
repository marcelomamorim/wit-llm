/*
 * @test
 * @summary Test LinkedBlockingDeque.put(E) method behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        testPutNullElement();
        testPutAndTake();
        testPutBlocksWhenFull() throws Exception {
            final LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(1);
            deque.put(1); // fill the deque
            Thread t = new Thread(new Runnable() {
                public void run() {
                    try {
                        deque.put(2); // should block until space available
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            t.start();
            Thread.sleep(100); // give time to block
            Integer val = deque.take(); // remove one element to unblock put
            t.join(1000);
            if (t.isAlive()) {
                throw new AssertionError("put did not unblock after space was available");
            }
            if (val == null || val != 1) {
                throw new AssertionError("Unexpected value taken from deque");
            }
        }
    }

    private static void testPutNullElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.put(null);
            throw new AssertionError("Expected NullPointerException when putting null");
        } catch (NullPointerException expected) {
            // expected
        } catch (InterruptedException e) {
            throw new AssertionError("Unexpected InterruptedException", e);
        }
    }

    private static void testPutAndTake() throws InterruptedException {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        String element = "test";
        deque.put(element);
        String result = deque.take();
        if (!element.equals(result)) {
            throw new AssertionError("Element taken does not match element put");
        }
    }
}