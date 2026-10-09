/* @test
   @summary Tests the putLast method of LinkedBlockingDeque.
   @run main LinkedBlockingDequeWitupTest_P6a2cf8a917c2
 */
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.Objects;

public class LinkedBlockingDequeWitupTest_P6a2cf8a917c2 {
    public static void main(String[] args) {
        testPutLastWithNull();
        testPutLastNormal();
        testPutLastInterrupted();
    }

    private static void testPutLastWithNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.putLast(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when putting null");
        }
    }

    private static void testPutLastNormal() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        String element = "test";
        try {
            deque.putLast(element);
        } catch (InterruptedException e) {
            throw new AssertionError("Unexpected InterruptedException");
        }
        if (Objects.equals(deque.peekLast(), element) == false) {
            throw new AssertionError("Element not correctly added to the end");
        }
    }

    private static void testPutLastInterrupted() {
        final LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        Thread thread = new Thread(() -> {
            try {
                deque.putLast("interrupted");
            } catch (InterruptedException e) {
                // ignore
            }
        });
        thread.start();
        try {
            Thread.sleep(50); // ensure thread is waiting on putLast
        } catch (InterruptedException e) {
            // ignore
        }
        thread.interrupt();
        try {
            thread.join();
        } catch (InterruptedException e) {
            // ignore
        }
        // After interruption, the element may or may not be added, but thread should not hang
        // We check that the thread has finished
        if (thread.isAlive()) {
            throw new AssertionError("Thread should have finished after interruption");
        }
    }
}
