/*
 * @test
 * @summary Verify that WeakCleanable.enqueue() throws UnsupportedOperationException with message "enqueue"
 * @modules java.base/jdk.internal.ref
 * @run main WeakCleanableWitupTest
 */

import java.lang.ref.Cleaner;
import java.lang.ref.Reference;
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

    public static void main(String[] args) {
        Cleaner cleaner = Cleaner.create();
        Object referent = new Object();
        TestCleanable cleanable = new TestCleanable(referent, cleaner);

        boolean thrown = false;
        try {
            cleanable.enqueue();
        } catch (UnsupportedOperationException e) {
            thrown = true;
            if (!"enqueue".equals(e.getMessage())) {
                throw new AssertionError("unexpected message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new AssertionError("expected UnsupportedOperationException from enqueue()");
        }

        Reference.reachabilityFence(referent);
        Reference.reachabilityFence(cleaner);
    }
}
