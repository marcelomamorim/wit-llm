/*
 * @test
 * @summary Test that WeakCleanable.isEnqueued() throws UnsupportedOperationException
 * @run main WeakCleanableWitupTest
 */

import jdk.internal.ref.WeakCleanable;
import java.lang.ref.Cleaner;

public class WeakCleanableWitupTest {

    public static void main(String[] args) {
        Cleaner cleaner = Cleaner.create();
        Object referent = new Object();
        WeakCleanable<Object> wc = new WeakCleanable<Object>(referent, cleaner) {
            @Override
            public void performCleanup() {
                // no-op
            }
        };

        try {
            wc.isEnqueued();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            if (!"isEnqueued".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
