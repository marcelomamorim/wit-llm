/*
 * @test
 * @summary Test SoftCleanable.enqueue() throws UnsupportedOperationException
 * @run main SoftCleanableWitupTest
 */

import jdk.internal.ref.SoftCleanable;

public class SoftCleanableWitupTest {

    static class DummySoftCleanable extends SoftCleanable<Object> {
        DummySoftCleanable(Object referent) {
            super(referent, java.lang.ref.Cleaner.create());
        }

        @Override
        public void clean() {
            // no-op
        }
    }

    public static void main(String[] args) {
        DummySoftCleanable sc = new DummySoftCleanable(new Object());
        try {
            sc.enqueue();
            throw new Error("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            if (!"enqueue".equals(e.getMessage())) {
                throw new Error("Exception message mismatch: " + e.getMessage());
            }
        }
    }
}
