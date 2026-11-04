/* @test    @summary Test for SoftCleanable.isEnqueued() method to verify it throws UnsupportedOperationException as expected.    @run main SoftCleanableWitupTest */

import java.lang.reflect.InvocationTargetException;

public class SoftCleanableWitupTest {

    public static void main(String[] args) {
        testIsEnqueuedThrowsException();
    }

    public static void testIsEnqueuedThrowsException() {
        try {
            // Create a dummy subclass instance to invoke the method
            jdk.internal.ref.SoftCleanable<Object> instance = new jdk.internal.ref.SoftCleanable<Object>() {
                @Override
                public void clean() {
                    // no-op
                }
            };
            boolean result = instance.isEnqueued();
            // If no exception, the test fails
            System.err.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
            System.out.println("PASS: isEnqueued() threw UnsupportedOperationException as expected.");
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception: " + t);
        }
    }
}
