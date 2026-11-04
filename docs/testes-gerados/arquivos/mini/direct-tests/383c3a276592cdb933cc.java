/*
 * @test
 * @summary Test SoftCleanable.isEnqueued() throws UnsupportedOperationException
 * @run main SoftCleanableWitupTest
 */

import jdk.internal.ref.SoftCleanable;
import java.lang.ref.Cleaner;

public class SoftCleanableWitupTest {

    static class TestSoftCleanable extends SoftCleanable<Object> {
        TestSoftCleanable(Object referent, Cleaner cleaner) {
            super(referent, cleaner);
        }

        @Override
        public void clean() {
            // no-op
        }
    }

    public static void main(String[] args) {
        Cleaner cleaner = Cleaner.create();
        Object referent = new Object();
        TestSoftCleanable sc = new TestSoftCleanable(referent, cleaner);

        try {
            sc.isEnqueued();
            throw new Error("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            if (!"isEnqueued".equals(e.getMessage())) {
                throw new Error("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
