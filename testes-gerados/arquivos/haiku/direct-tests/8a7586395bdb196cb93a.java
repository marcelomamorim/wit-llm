/*
 * @test
 * @summary SoftCleanable.enqueue() must always throw UnsupportedOperationException with message "enqueue"
 * @modules java.base/jdk.internal.ref
 * @run main SoftCleanableWitupTest
 */

import java.lang.ref.Cleaner;
import java.lang.ref.Reference;
import jdk.internal.ref.SoftCleanable;

public class SoftCleanableWitupTest {

    static class Impl extends SoftCleanable<Object> {
        Impl(Object referent, Cleaner cleaner) {
            super(referent, cleaner);
        }

        @Override
        protected void performCleanup() {
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Cleaner cleaner = Cleaner.create();
        Object referent = new Object();
        Impl cleanable = new Impl(referent, cleaner);

        for (int i = 0; i < 2; i++) {
            boolean thrown = false;
            try {
                cleanable.enqueue();
            } catch (UnsupportedOperationException e) {
                thrown = true;
                check("enqueue".equals(e.getMessage()),
                      "unexpected message: " + e.getMessage());
            }
            check(thrown, "enqueue() did not throw UnsupportedOperationException");
        }

        Reference.reachabilityFence(referent);
        Reference.reachabilityFence(cleaner);
    }
}
