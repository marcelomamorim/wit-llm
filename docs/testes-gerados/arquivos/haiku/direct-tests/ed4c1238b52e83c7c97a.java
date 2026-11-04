/*
 * @test
 * @summary Verify that the iterator returned by keySet() of ParallelListResourceBundle rejects remove() with UnsupportedOperationException
 * @modules java.base/sun.util.resources
 * @run main ParallelListResourceBundleWitupTest
 */

import java.util.Iterator;
import sun.util.resources.ParallelListResourceBundle;

public class ParallelListResourceBundleWitupTest {

    static class TestBundle extends ParallelListResourceBundle {
        @Override
        protected Object[][] getContents() {
            return new Object[][] {
                {"a", "1"},
                {"b", "2"}
            };
        }
    }

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    static void testRemoveAfterNextThrowsUOE() {
        TestBundle rb = new TestBundle();
        Iterator<String> it = rb.keySet().iterator();
        if (!it.hasNext()) {
            fail("expected at least one key");
        }
        it.next();
        try {
            it.remove();
            fail("remove() did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    static void testRemoveBeforeNextThrowsUOE() {
        TestBundle rb = new TestBundle();
        Iterator<String> it = rb.keySet().iterator();
        try {
            it.remove();
            fail("remove() before next() did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    public static void main(String[] args) {
        testRemoveAfterNextThrowsUOE();
        testRemoveBeforeNextThrowsUOE();
    }
}
