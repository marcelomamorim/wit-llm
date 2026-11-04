/* @test    @summary Test for ReentrantReadWriteLock.hasQueuedThread(Thread) method to observe behavior with null and non-null threads    @run main ReentrantReadWriteLockWitupTest */

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockWitupTest {

    public static void main(String[] args) {
        testHasQueuedThreadWithNull();
        testHasQueuedThreadWithNewThread();
    }

    private static void testHasQueuedThreadWithNull() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        try {
            boolean result = lock.hasQueuedThread(null);
            System.out.println("hasQueuedThread(null) = " + result);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException thrown as expected for null thread");
        }
    }

    private static void testHasQueuedThreadWithNewThread() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        Thread thread = new Thread();
        boolean result = lock.hasQueuedThread(thread);
        System.out.println("hasQueuedThread(new Thread()) = " + result);
    }
}
