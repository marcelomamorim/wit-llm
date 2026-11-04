/*
 * @test
 * @summary Test LinkedBlockingQueue.take() blocking and unblocking behavior
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testTakeBlocksUntilElementAvailable();
        testTakeReturnsElement();
    }

    static void testTakeBlocksUntilElementAvailable() throws Exception {
        final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        final Thread taker = new Thread(new Runnable() {
            volatile String result = null;
            volatile Throwable error = null;
            public void run() {
                try {
                    result = queue.take();
                } catch (Throwable t) {
                    error = t;
                }
            }
        });

        taker.start();

        // Wait briefly to ensure taker thread is blocked on take()
        Thread.sleep(200);

        if (!taker.isAlive()) {
            throw new AssertionError("take() did not block when queue was empty");
        }

        // Add element to unblock taker
        String element = "testElement";
        queue.put(element);

        // Wait for taker to finish
        taker.join(1000);

        if (taker.isAlive()) {
            throw new AssertionError("take() did not return after element was added");
        }

        // Check that taker thread got the correct element
        try {
            java.lang.reflect.Field resultField = taker.getClass().getDeclaredField("result");
            resultField.setAccessible(true);
            Object result = resultField.get(taker);
            if (!element.equals(result)) {
                throw new AssertionError("take() returned wrong element: expected " + element + " but got " + result);
            }
            java.lang.reflect.Field errorField = taker.getClass().getDeclaredField("error");
            errorField.setAccessible(true);
            Object error = errorField.get(taker);
            if (error != null) {
                throw new AssertionError("take() threw exception: " + error);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new AssertionError("Reflection failed: " + e);
        }
    }

    static void testTakeReturnsElement() throws Exception {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.put(42);
        int val = queue.take();
        if (val != 42) {
            throw new AssertionError("take() returned wrong element: expected 42 but got " + val);
        }
    }
}
