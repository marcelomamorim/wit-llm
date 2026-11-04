/*
 * @test
 * @summary Test that PhantomCleanable.enqueue() throws UnsupportedOperationException
 * @run main PhantomCleanableWitupTest
 */

import jdk.internal.ref.PhantomCleanable;
import java.lang.ref.Cleaner;

public class PhantomCleanableWitupTest {

    public static void main(String[] args) {
        Cleaner cleaner = Cleaner.create();
        Object referent = new Object();

        PhantomCleanable<Object> pc = new PhantomCleanable<Object>(referent, cleaner) {
            @Override
            public void performCleanup() {
                // no-op
            }
        };

        try {
            pc.enqueue();
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            if (!"enqueue".equals(e.getMessage())) {
                throw new AssertionError("Exception message mismatch: " + e.getMessage());
            }
        }
    }
}
