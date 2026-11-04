/* @test    @summary Test for ReentrantReadWriteLock.newCondition() method to verify it throws UnsupportedOperationException as expected.    @run main ReentrantReadWriteLockWitupTest */

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockWitupTest {

    public static void main(String[] args) {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        try {
            lock.newCondition();
            System.out.println("Test Failed: No exception thrown");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test Passed: UnsupportedOperationException thrown as expected");
        } catch (Throwable t) {
            System.out.println("Test Failed: Unexpected exception: " + t);
        }
    }
}
