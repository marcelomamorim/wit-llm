/*
 * @test
 * @summary Test that PhantomCleanable.enqueue() throws UnsupportedOperationException
 * @run main PhantomCleanableWitupTest
 */

import jdk.internal.ref.PhantomCleanable;

public class PhantomCleanableWitupTest {

    public static void main(String[] args) {
        PhantomCleanable<Object> pc = new PhantomCleanable<Object>() {
            @Override
            public void performCleanup() {
                // no-op
            }
        };

        boolean thrown = false;
        try {
            pc.enqueue();
        } catch (UnsupportedOperationException e) {
            thrown = true;
            if (!"enqueue".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }

        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        }
    }
}
