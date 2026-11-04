/*
 * @test
 * @summary Verify that PhantomCleanable.isEnqueued() throws UnsupportedOperationException with message "isEnqueued"
 * @modules java.base/jdk.internal.ref
 * @run main PhantomCleanableWitupTest
 */

import java.lang.ref.Cleaner;
import java.lang.ref.Reference;
import jdk.internal.ref.PhantomCleanable;

public class PhantomCleanableWitupTest {

    public static void main(String[] args) {
        Cleaner cleaner = Cleaner.create();
        Object referent = new Object();
        PhantomCleanable<Object> cleanable = new PhantomCleanable<Object>(referent, cleaner) {
            @Override
            protected void performCleanup() {
            }
        };
        try {
            for (int i = 0; i < 2; i++) {
                try {
                    cleanable.isEnqueued();
                    throw new AssertionError("expected UnsupportedOperationException on call " + i);
                } catch (UnsupportedOperationException e) {
                    if (!"isEnqueued".equals(e.getMessage())) {
                        throw new AssertionError("unexpected message: " + e.getMessage(), e);
                    }
                }
            }
        } finally {
            Reference.reachabilityFence(referent);
            Reference.reachabilityFence(cleaner);
        }
    }
}
