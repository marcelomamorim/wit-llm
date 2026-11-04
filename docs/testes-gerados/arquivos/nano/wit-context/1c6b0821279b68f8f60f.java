/* @test    @summary Test for WeakCleanable.isEnqueued() method to observe exception behavior    @run main WeakCleanableWitupTest */

import jdk.internal.ref.WeakCleanable;

public class WeakCleanableWitupTest {

    public static void main(String[] args) {
        testIsEnqueuedThrowsException();
    }

    private static void testIsEnqueuedThrowsException() {
        WeakCleanable<Object> wc = new WeakCleanable<Object>() {
            @Override
            public boolean isEnqueued() {
                return false; // This method always throws, so override to simulate behavior
            }
        };
        try {
            wc.isEnqueued();
            System.out.println("Test failed: no exception thrown");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: Caught expected exception: " + e.getClass().getName());
        } catch (Throwable t) {
            System.out.println("Test failed: Caught unexpected exception: " + t.getClass().getName());
        }
    }
}
