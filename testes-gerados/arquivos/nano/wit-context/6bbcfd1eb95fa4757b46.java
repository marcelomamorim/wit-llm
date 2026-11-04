/*
 * @test
 * @summary Test for ArrayBlockingQueue.take() method to observe exception behavior.
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testTakeInterruptedException();
        testTakeUnsupportedOperationException();
    }

    private static void testTakeInterruptedException() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        Thread t = new Thread(() -> {
            try {
                queue.take();
            } catch (InterruptedException e) {
                System.out.println("Caught InterruptedException as expected");
            }
        });
        t.start();
        t.interrupt();
        t.join();
    }

    private static void testTakeUnsupportedOperationException() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10) {
            @Override
            public Integer take() throws InterruptedException {
                throw new UnsupportedOperationException("Simulated unsupported operation");
            }
        };
        try {
            queue.take();
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught UnsupportedOperationException as expected");
        }
    }
}
