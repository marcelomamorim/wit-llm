/*
 * @test
 * @summary Test for CleanerImpl.get() method.
 * @run main CleanerImplWitupTest
 */

import jdk.internal.ref.CleanerImpl;

public class CleanerImplWitupTest {

    public static void main(String[] args) {
        testGetMethodThrowsException();
    }

    public static void testGetMethodThrowsException() {
        CleanerImpl cleanerImpl = new CleanerImpl();
        try {
            Object result = cleanerImpl.get();
            // If no exception, the test fails
            System.err.println("Test failed: Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
            System.out.println("Test passed: UnsupportedOperationException was thrown as expected.");
        } catch (Throwable t) {
            System.err.println("Test failed: Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
