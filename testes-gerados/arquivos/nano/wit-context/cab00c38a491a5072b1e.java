/*
 * @test
 * @summary Test for jdk.internal.ref.PhantomCleanable
 * @run main PhantomCleanableWitupTest
 */

import java.lang.ref.Cleaner;
import jdk.internal.ref.PhantomCleanable;

public class PhantomCleanableWitupTest {

    public static void main(String[] args) {
        testIsEnqueuedThrowsException();
    }

    public static void testIsEnqueuedThrowsException() {
        PhantomCleanable<Object> pc = new PhantomCleanable<Object>() {
            @Override
            public void performCleanup() {
                // no-op
            }
        };
        try {
            boolean result = pc.isEnqueued();
            System.out.println("isEnqueued() returned: " + result);
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught expected exception: " + e.getClass().getName());
        }
    }
}
