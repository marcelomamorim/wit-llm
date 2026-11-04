/* @test    @summary Test for SoftCleanable.isEnqueued() method    @run main SoftCleanableWitupTest */

import jdk.internal.ref.SoftCleanable;

public class SoftCleanableWitupTest {

    public static void main(String[] args) {
        testIsEnqueuedThrows();
    }

    private static void testIsEnqueuedThrows() {
        try {
            SoftCleanable<Object> sc = new SoftCleanable<Object>() {
                @Override
                public boolean isEnqueued() {
                    return super.isEnqueued();
                }
            };
            sc.isEnqueued();
            System.out.println("Test failed: no exception thrown");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: UnsupportedOperationException thrown as expected");
        } catch (Throwable t) {
            System.out.println("Test failed: unexpected exception: " + t);
        }
    }
}
