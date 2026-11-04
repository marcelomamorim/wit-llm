/*
 * @test
 * @summary Test jdk.internal.ref.WeakCleanable.enqueue() throws UnsupportedOperationException
 * @run main WeakCleanableWitupTest
 */

import jdk.internal.ref.WeakCleanable;
import java.lang.ref.Cleaner;

public class WeakCleanableWitupTest {

    static class TestWeakCleanable extends WeakCleanable<Object> {
        TestWeakCleanable(Object referent, Cleaner cleaner) {
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
        TestWeakCleanable wc = new TestWeakCleanable(referent, cleaner);

        boolean thrown = false;
        try {
            wc.enqueue();
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
