/* @test    @summary Test takeLast method of LinkedBlockingDeque    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) throws InterruptedException {
        testTakeLastEmptyDeque();
        testTakeLastWithElements();
    }

    static void testTakeLastEmptyDeque() throws InterruptedException {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        Thread t = new Thread(() -> {
            try {
                String result = deque.takeLast();
                System.out.println("Unexpected success: " + result);
            } catch (InterruptedException e) {
                System.out.println("InterruptedException occurred");
            } catch (NoSuchElementException e) {
                System.out.println("NoSuchElementException occurred");
            }
        });
        t.start();
        Thread.sleep(100); // Ensure thread is waiting on takeLast
        deque.offerLast("element");
        t.join();
    }

    static void testTakeLastWithElements() throws InterruptedException {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.offerLast("first");
        deque.offerLast("second");
        String last = deque.takeLast();
        assert last.equals("second") : "Expected 'second', got '" + last + "'";
        last = deque.takeLast();
        assert last.equals("first") : "Expected 'first', got '" + last + "'";
    }
}
