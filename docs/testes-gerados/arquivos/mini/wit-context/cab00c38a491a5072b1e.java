/*
 * @test
 * @summary Test jdk.internal.ref.PhantomCleanable.isEnqueued() throws UnsupportedOperationException
 * @run main PhantomCleanableWitupTest
 */

import jdk.internal.ref.PhantomCleanable;

public class PhantomCleanableWitupTest {

    static class DummyCleaner extends PhantomCleanable<Object> {
        DummyCleaner(Object referent) {
            super(referent, java.lang.ref.Cleaner.create());
        }
        @Override
        public void performCleanup() {
            // no-op
        }
    }

    public static void main(String[] args) {
        Object referent = new Object();
        DummyCleaner pc = new DummyCleaner(referent);
        try {
            pc.isEnqueued();
            throw new Error("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            if (!"isEnqueued".equals(e.getMessage())) {
                throw new Error("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
