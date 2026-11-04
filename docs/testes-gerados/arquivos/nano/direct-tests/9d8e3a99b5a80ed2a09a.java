/* @test    @summary Test for CleanerImpl.get() method to ensure it throws UnsupportedOperationException.    @run main CleanerImplWitupTest */

import jdk.internal.ref.CleanerImpl;

public class CleanerImplWitupTest {

    public static void main(String[] args) {
        testGetMethodThrows();
    }

    public static void testGetMethodThrows() {
        CleanerImpl cleanerImpl = new CleanerImpl();
        try {
            cleanerImpl.get();
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is thrown
            if (!"get".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
