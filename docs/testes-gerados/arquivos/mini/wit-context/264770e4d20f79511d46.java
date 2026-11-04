/*
 * @test
 * @summary Test jdk.internal.ref.WeakCleanable.enqueue() throws UnsupportedOperationException
 * @run main WeakCleanableWitupTest
 */

public class WeakCleanableWitupTest {

    public static void main(String[] args) {
        WeakCleanableWitupTest test = new WeakCleanableWitupTest();
        test.testEnqueueThrows();
    }

    private void testEnqueueThrows() {
        // Create a minimal subclass to instantiate abstract WeakCleanable
        class TestWeakCleanable extends jdk.internal.ref.WeakCleanable<Object> {
            TestWeakCleanable(Object referent, java.lang.ref.Cleaner cleaner) {
                super(referent, cleaner);
            }
            @Override
            public void clean() {
                // no-op
            }
        }

        java.lang.ref.Cleaner cleaner = java.lang.ref.Cleaner.create();
        Object referent = new Object();
        TestWeakCleanable wc = new TestWeakCleanable(referent, cleaner);

        boolean threw = false;
        try {
            wc.enqueue();
        } catch (UnsupportedOperationException e) {
            threw = true;
            if (!"enqueue".equals(e.getMessage())) {
                throw new AssertionError("Exception message mismatch", e);
            }
        }
        if (!threw) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown by enqueue()");
        }
    }
}
