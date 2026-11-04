/*
 * @test
 * @summary Verifies that PhantomCleanable.enqueue() always throws UnsupportedOperationException
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
            protected void performCleanup() {
            }
        };

        expectUnsupported(pc);
        expectUnsupported(pc);

        Reference.reachabilityFence(referent);
        Reference.reachabilityFence(pc);
    }

    private static void expectUnsupported(PhantomCleanable<?> pc) {
        try {
            pc.enqueue();
        } catch (UnsupportedOperationException expected) {
            if (!"enqueue".equals(expected.getMessage())) {
                throw new RuntimeException("Unexpected message: " + expected.getMessage());
            }
            return;
        }
        throw new RuntimeException("enqueue() did not throw UnsupportedOperationException");
    }
}
