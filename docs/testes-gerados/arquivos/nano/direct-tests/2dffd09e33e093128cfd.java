/* @test    @summary Test the get() method of CleanerImpl class.    @run main CleanerImplWitupTest */

import jdk.internal.ref.CleanerImpl;

public class CleanerImplWitupTest {

    public static void main(String[] args) {
        testGetMethodThrowsException();
    }

    private static void testGetMethodThrowsException() {
        CleanerImpl cleanerImpl = new CleanerImpl();
        boolean exceptionThrown = false;
        try {
            cleanerImpl.get();
        } catch (UnsupportedOperationException e) {
            exceptionThrown = true;
            assert "get".equals(e.getMessage()) : "Unexpected exception message";
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        }
    }
}
