/* @test    @summary Test remove() method throws UnsupportedOperationException in ParallelListResourceBundle    @run main ParallelListResourceBundleWitupTest */

import sun.util.resources.ParallelListResourceBundle;

public class ParallelListResourceBundleWitupTest {

    public static void main(String[] args) {
        testRemoveThrowsException();
    }

    public static void testRemoveThrowsException() {
        ParallelListResourceBundle bundle = new ParallelListResourceBundle() {
            @Override
            protected Object[][] getContents() {
                return new Object[][] { {"key", "value"} };
            }
        };
        boolean exceptionThrown = false;
        try {
            bundle.remove();
        } catch (UnsupportedOperationException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected UnsupportedOperationException to be thrown by remove()");
        }
    }
}
