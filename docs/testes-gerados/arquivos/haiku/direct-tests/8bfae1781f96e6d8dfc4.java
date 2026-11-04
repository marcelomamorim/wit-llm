/*
 * @test
 * @summary Verify PhantomCleanable.enqueue() always throws UnsupportedOperationException with message "enqueue"
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
        PhantomCleanable<Object> pc = new PhantomCleanable<Object>(referent, cleaner) {
            @Override
            public void performCleanup() {
            }
        };

        for (int i = 0; i < 2; i++) {
            try {
                pc.enqueue();
                throw new RuntimeException("expected UnsupportedOperationException on call " + i);
            } catch (UnsupportedOperationException e) {
                if (!"enqueue".equals(e.getMessage())) {
                    throw new RuntimeException("wrong message: " + e.getMessage());
                }
            }
        }

        Reference.reachabilityFence(referent);
        Reference.reachabilityFence(cleaner);
    }
}
