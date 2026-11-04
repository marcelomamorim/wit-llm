/*
 * @test
 * @summary Test LinkedBlockingDeque.take() method behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        testTakeBlocksUntilElementAvailable();
        testTakeReturnsElementWhenAvailable();
    }

    static void testTakeReturnsElementWhenAvailable() throws Exception {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.put("test");
        String result = deque.take();
        if (!"test".equals(result)) {
            throw new AssertionError("Expected 'test' but got: " + result);
        }
    }

    static void testTakeBlocksUntilElementAvailable() throws Exception {
        final LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        Thread taker = new Thread(new Runnable() {
            public void run() {
                try {
                    String val = deque.take();
                    if (!"delayed".equals(val)) {
                        throw new AssertionError("Expected 'delayed' but got: " + val);
                    }
                } catch (InterruptedException e) {
                    throw new AssertionError("Interrupted while waiting in take");
                }
            }
        });
        taker.start();

        // Sleep briefly to ensure taker thread is blocked on take()
        Thread.sleep(100);

        deque.put("delayed");

        taker.join(1000);
        if (taker.isAlive()) {
            throw new AssertionError("take() did not return after element was put");
        }
    }
}
