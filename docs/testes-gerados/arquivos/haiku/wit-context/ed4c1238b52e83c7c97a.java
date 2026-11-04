/*
 * @test
 * @summary Verifies that the iterator returned by ParallelListResourceBundle keySet() rejects remove() with UnsupportedOperationException
 * @modules java.base/sun.util.resources
 * @run main ParallelListResourceBundleWitupTest
 */

import java.util.Iterator;
import java.util.Set;
import sun.util.resources.ParallelListResourceBundle;

public class ParallelListResourceBundleWitupTest {

    static class TestBundle extends ParallelListResourceBundle {
        @Override
        protected Object[][] getContents() {
            return new Object[][] { { "key1", "value1" } };
        }
    }

    public static void main(String[] args) throws Exception {
        TestBundle bundle = new TestBundle();
        Set<String> keys = bundle.keySet();
        Iterator<String> it = keys.iterator();
        if (!it.hasNext()) {
            throw new RuntimeException("Expected at least one key");
        }
        it.next();
        boolean thrown = false;
        try {
            it.remove();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException from remove()");
        }
    }
}
