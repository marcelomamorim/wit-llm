/*
 * @test
 * @summary Test for LinkedBlockingDeque.take() method.
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) throws InterruptedException {
        testTakeWithEmptyDeque();
        testTakeWithNonEmptyDeque();
    }

    private static void testTakeWithEmptyDeque() throws InterruptedException {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        Thread t = new Thread(() -> {
            try {
                Integer value = deque.take();
                System.out.println("Unexpected value: " + value);
            } catch (InterruptedException e) {
                System.out.println("InterruptedException occurred");
            } catch (NoSuchElementException e) {
                System.out.println("NoSuchElementException occurred");
            }
        });
        t.start();
        Thread.sleep(100); // Ensure thread is waiting on take()
        deque.offer(42); // Insert element to unblock take()
        t.join();
    }

    private static void testTakeWithNonEmptyDeque() throws InterruptedException {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.offer(99);
        Integer result = deque.take();
        if (result != null && result == 99) {
            System.out.println("testTakeWithNonEmptyDeque passed");
        } else {
            System.out.println("testTakeWithNonEmptyDeque failed");
        }
    }
}
