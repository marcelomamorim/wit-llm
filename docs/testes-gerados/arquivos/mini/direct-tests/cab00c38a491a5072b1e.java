/*
 * @test
 * @summary Test that PhantomCleanable.isEnqueued() throws UnsupportedOperationException
 * @run main PhantomCleanableWitupTest
 */

public class PhantomCleanableWitupTest {
    public static void main(String[] args) {
        final jdk.internal.ref.PhantomCleanable<Object> pc = new jdk.internal.ref.PhantomCleanable<Object>() {
            @Override
            public void performCleanup() {
                // no-op
            }
        };

        try {
            pc.isEnqueued();
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            if (!"isEnqueued".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
