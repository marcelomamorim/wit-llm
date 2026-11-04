/*
 * @test
 * @summary Test the get() method of CleanerImpl class.
 * @run main CleanerImplGetMethodTest
 */

import jdk.internal.ref.CleanerImpl;

public class CleanerImplGetMethodTest {

    public static void main(String[] args) {
        testGetMethodThrowsException();
    }

    public static void testGetMethodThrowsException() {
        CleanerImpl cleanerImpl = new CleanerImpl();
        try {
            cleanerImpl.get();
            System.out.println("Test failed: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            if ("get".equals(e.getMessage())) {
                System.out.println("Test passed: UnsupportedOperationException thrown as expected.");
            } else {
                System.out.println("Test failed: Exception message mismatch.");
            }
        } catch (Throwable t) {
            System.out.println("Test failed: Unexpected exception type: " + t);
        }
    }
}
