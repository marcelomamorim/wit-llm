/*
 * @test
 * @summary PhantomCleanable.isEnqueued() is unsupported and must throw UnsupportedOperationException
 * @modules java.base/jdk.internal.ref
 * @run main PhantomCleanableWitupTest
 */
import java.lang.ref.Cleaner;
import java.lang.ref.Reference;
import jdk.internal.ref.PhantomCleanable;

public class PhantomCleanableWitupTest {

    static class TestCleanable extends PhantomCleanable<Object> {
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
        TestCleanable c = new TestCleanable(referent, cleaner);
        try {
            c.isEnqueued();
            throw new RuntimeException("expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            if (!"isEnqueued".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        } finally {
            Reference.reachabilityFence(referent);
        }
    }
}
