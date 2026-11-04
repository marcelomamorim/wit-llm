/*
 * @test
 * @summary Test for CleanerImpl.clear() method.
 * @run main CleanerImplWitupTest
 */

import jdk.internal.ref.CleanerImpl;

public class CleanerImplWitupTest {

    public static void main(String[] args) {
        testClearThrowsUnsupportedOperationException();
    }

    public static void testClearThrowsUnsupportedOperationException() {
        CleanerImpl cleanerImpl = new CleanerImpl();
        try {
            cleanerImpl.clear();
            System.out.println("Test failed: no exception thrown");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: UnsupportedOperationException caught as expected");
        } catch (Throwable t) {
            System.out.println("Test failed: unexpected exception: " + t);
        }
    }
}
