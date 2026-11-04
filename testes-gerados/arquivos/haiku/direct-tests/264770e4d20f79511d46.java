/*
 * @test
 * @summary Verify that WeakCleanable.enqueue() always throws UnsupportedOperationException
 * @modules java.base/jdk.internal.ref
 * @run main WeakCleanableWitupTest
 */

import java.lang.ref.Cleaner;
import jdk.internal.ref.WeakCleanable;

public class WeakCleanableWitupTest {

    static final class TestCleanable extends WeakCleanable<Object> {
        TestCleanable(Object referent, Cleaner cleaner) {
            super(referent, cleaner);
        }

        @Override
        protected void performCleanup() {
        }
    }

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    public static void main(String[] args) {
        Cleaner cleaner = Cleaner.create();
        Object referent = new Object();
        TestCleanable c = new TestCleanable(referent, cleaner);

        boolean thrown = false;
        String message = null;
        try {
            c.enqueue();
        } catch (UnsupportedOperationException e) {
            thrown = true;
            message = e.getMessage();
        }
        if (!thrown) {
            fail("enqueue() did not throw UnsupportedOperationException");
        }
        if (!"enqueue".equals(message)) {
            fail("unexpected message: " + message);
        }

        thrown = false;
        try {
            c.enqueue();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            fail("second enqueue() call did not throw UnsupportedOperationException");
        }

        java.lang.ref.Reference.reachabilityFence(referent);
        java.lang.ref.Reference.reachabilityFence(cleaner);
    }
}
