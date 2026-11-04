/*
 * @test
 * @summary Test for jdk.internal.ref.CleanerImpl.get()
 * @run main CleanerImplGetTest
 */

import jdk.internal.ref.CleanerImpl;

public class CleanerImplGetTest {

    public static void main(String[] args) {
        testGetMethodThrowsException();
    }

    public static void testGetMethodThrowsException() {
        CleanerImpl instance = new CleanerImpl();
        try {
            Object result = instance.get();
            // Expecting an UnsupportedOperationException
            System.err.println("Expected UnsupportedOperationException, but got: " + result);
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is thrown
            System.out.println("Caught expected exception: " + e);
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
