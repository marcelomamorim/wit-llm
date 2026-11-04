/* @test    @summary Test for ReentrantReadWriteLock.lock() method to observe behavior and exception paths    @run main ReentrantReadWriteLockWitupTest */

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockWitupTest {

    public static void main(String[] args) {
        testLockMethod();
    }

    private static void testLockMethod() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        try {
            lock.lock();
            System.out.println("lock() executed without exception.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught expected UnsupportedOperationException.");
        } catch (Exception e) {
            System.out.println("Caught unexpected exception: " + e);
        }
    }
}
