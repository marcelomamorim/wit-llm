/* @test    @summary Test ReentrantReadWriteLock.newCondition() throws UnsupportedOperationException    @run main ReentrantReadWriteLockWitupTest */

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockWitupTest {

    public static void main(String[] args) {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        try {
            lock.newCondition();
            System.out.println("Test failed: no exception thrown");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: UnsupportedOperationException thrown as expected");
        } catch (Throwable t) {
            System.out.println("Test failed: unexpected exception: " + t);
        }
    }
}
