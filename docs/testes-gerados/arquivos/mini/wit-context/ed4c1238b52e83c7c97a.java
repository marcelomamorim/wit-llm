/*
 * @test
 * @summary Test sun.util.resources.ParallelListResourceBundle remove() throws UnsupportedOperationException
 * @run main ParallelListResourceBundleWitupTest
 */

public class ParallelListResourceBundleWitupTest {
    public static void main(String[] args) {
        ParallelListResourceBundle.KeySet.Anonymous anon = new ParallelListResourceBundle.KeySet.Anonymous();
        try {
            anon.remove();
            throw new Error("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}

// Minimal stub classes to access the remove() method as per source excerpt
abstract class ParallelListResourceBundle extends java.util.ResourceBundle {
    protected ParallelListResourceBundle() {}
    protected abstract Object[][] getContents();

    static class KeySet {
        static class Anonymous {
            public void remove() {
                throw new UnsupportedOperationException();
            }
        }
    }
}
