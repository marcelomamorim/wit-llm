/*
 * @test
 * @summary Verify SoftCleanable.isEnqueued currently throws UnsupportedOperationException with message isEnqueued
 * @modules java.base/jdk.internal.ref
 * @run main SoftCleanableWitupTest
 */

import java.lang.ref.Cleaner;

public class SoftCleanableWitupTest {

    public static void main(String[] args) {
        Cleaner cleaner = Cleaner.create();
        Object referent = new Object();
        jdk.internal.ref.SoftCleanable<Object> sc =
            new jdk.internal.ref.SoftCleanable<Object>(referent, cleaner) {
                @Override
                protected void performCleanup() {
                }
            };
        try {
            sc.isEnqueued();
            throw new AssertionError("expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            if (!"isEnqueued".equals(e.getMessage())) {
                throw new AssertionError("unexpected message: " + e.getMessage());
            }
        }
    }
}
