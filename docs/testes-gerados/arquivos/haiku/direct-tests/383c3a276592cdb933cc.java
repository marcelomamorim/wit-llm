/*
 * @test
 * @summary SoftCleanable.isEnqueued() must always throw UnsupportedOperationException
 * @modules java.base/jdk.internal.ref
 * @run main SoftCleanableWitupTest
 */

import java.lang.ref.Cleaner;
import jdk.internal.ref.SoftCleanable;

public class SoftCleanableWitupTest {

    static class TestCleanable extends SoftCleanable<Object> {
        TestCleanable(Object referent, Cleaner cleaner) {
            super(referent, cleaner);
        }

        @Override
        protected void performCleanup() {
        }
    }

    static void checkIsEnqueuedThrows(SoftCleanable<?> c) {
        boolean thrown = false;
        try {
            c.isEnqueued();
        } catch (UnsupportedOperationException e) {
            thrown = true;
            if (!"isEnqueued".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new RuntimeException("UnsupportedOperationException not thrown");
        }
    }

    public static void main(String[] args) {
        Cleaner cleaner = Cleaner.create();
        Object referent = new Object();
        TestCleanable c = new TestCleanable(referent, cleaner);
        checkIsEnqueuedThrows(c);
        checkIsEnqueuedThrows(c);
        java.lang.ref.Reference.reachabilityFence(referent);
        java.lang.ref.Reference.reachabilityFence(c);
    }
}
