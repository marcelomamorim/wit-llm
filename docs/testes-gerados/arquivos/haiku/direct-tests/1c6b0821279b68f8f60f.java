/*
 * @test
 * @summary WeakCleanable.isEnqueued() always throws UnsupportedOperationException with message "isEnqueued"
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
        try {
            cleanable.isEnqueued();
            throw new AssertionError("expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            if (!"isEnqueued".equals(e.getMessage())) {
                throw new AssertionError("unexpected message: " + e.getMessage());
            }
        } finally {
            Reference.reachabilityFence(referent);
            Reference.reachabilityFence(cleaner);
        }
    }
}
