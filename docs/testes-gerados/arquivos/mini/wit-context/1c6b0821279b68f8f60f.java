/*
 * @test
 * @summary Test jdk.internal.ref.WeakCleanable.isEnqueued() throws UnsupportedOperationException
 * @run main WeakCleanableWitupTest
 */

import jdk.internal.ref.WeakCleanable;
import java.lang.ref.Cleaner;

public class WeakCleanableWitupTest {

    static class DummyWeakCleanable extends WeakCleanable<Object> {
        DummyWeakCleanable(Object referent, Cleaner cleaner) {
            super(referent, cleaner);
        }

        @Override
        public void clean() {
            // no-op
        }
    }

    public static void main(String[] args) {
        Cleaner cleaner = Cleaner.create();
        Object referent = new Object();
        DummyWeakCleanable wc = new DummyWeakCleanable(referent, cleaner);

        boolean thrown = false;
        try {
            wc.isEnqueued();
        } catch (UnsupportedOperationException e) {
            thrown = true;
            if (!"isEnqueued".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }

        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        }
    }
}
