/*
 * @test
 * @summary Verify that WeakCleanable.isEnqueued() currently throws UnsupportedOperationException
 * @modules java.base/jdk.internal.ref
 * @run main WeakCleanableWitupTest
 */

import java.lang.ref.Cleaner;
import jdk.internal.ref.WeakCleanable;

public class WeakCleanableWitupTest {

    public static void main(String[] args) {
        Cleaner cleaner = Cleaner.create();
        Object referent = new Object();
        WeakCleanable<Object> cleanable = new WeakCleanable<Object>(referent, cleaner) {
            @Override
            protected void performCleanup() {
            }
        };
        try {
            cleanable.isEnqueued();
            throw new RuntimeException("Expected UnsupportedOperationException from isEnqueued()");
        } catch (UnsupportedOperationException e) {
            if (!"isEnqueued".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage(), e);
            }
        }
        Cleaner unusedCleaner = cleaner;
        Object unusedReferent = referent;
    }
}
