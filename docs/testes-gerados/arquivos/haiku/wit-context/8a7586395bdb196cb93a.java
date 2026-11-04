/*
 * @test
 * @summary Verify SoftCleanable.enqueue() throws UnsupportedOperationException with message "enqueue"
 * @modules java.base/jdk.internal.ref
 * @run main SoftCleanableWitupTest
 */
import java.lang.ref.Cleaner;
import java.lang.ref.Reference;
import jdk.internal.ref.SoftCleanable;

public class SoftCleanableWitupTest {
    public static void main(String[] args) {
        Cleaner cleaner = Cleaner.create();
        Object referent = new Object();
        SoftCleanable<Object> cleanable = new SoftCleanable<Object>(referent, cleaner) {
            @Override
            protected void performCleanup() {
            }
        };
        String message = null;
        boolean thrown = false;
        try {
            cleanable.enqueue();
        } catch (UnsupportedOperationException e) {
            thrown = true;
            message = e.getMessage();
        }
        if (!thrown) {
            throw new RuntimeException("expected UnsupportedOperationException from enqueue()");
        }
        if (!"enqueue".equals(message)) {
            throw new RuntimeException("unexpected message: " + message);
        }
        Reference.reachabilityFence(referent);
        Reference.reachabilityFence(cleaner);
    }
}
