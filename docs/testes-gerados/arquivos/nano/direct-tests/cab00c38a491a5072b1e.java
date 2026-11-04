/* @test    @summary Test for PhantomCleanable.isEnqueued() method    @run main PhantomCleanableWitupTest */

import java.lang.ref.Cleaner;
import jdk.internal.ref.PhantomCleanable;

public class PhantomCleanableWitupTest {

    public static void main(String[] args) {
        testIsEnqueuedThrows();
    }

    private static void testIsEnqueuedThrows() {
        PhantomCleanable<Object> cleanable = new PhantomCleanable<Object>() {
            @Override
            public boolean isEnqueued() {
                return false;
            }
        };
        try {
            cleanable.isEnqueued();
            System.out.println("Test failed: no exception thrown");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: exception thrown as expected");
        } catch (Throwable t) {
            System.out.println("Test failed: unexpected exception: " + t);
        }
    }
}
