/* @test    @summary Test for ReentrantReadWriteLock.unlock() method to verify exception behavior.    @run main ReentrantReadWriteLockWitupTest */

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockWitupTest {

    public static void main(String[] args) {
        testUnlockWithoutLock();
    }

    private static void testUnlockWithoutLock() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        try {
            lock.unlock();
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: Caught expected UnsupportedOperationException.");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }
}
