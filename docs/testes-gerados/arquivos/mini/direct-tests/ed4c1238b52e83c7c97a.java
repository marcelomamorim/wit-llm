/*
 * @test
 * @summary Test that ParallelListResourceBundle.remove() throws UnsupportedOperationException
 * @run main ParallelListResourceBundleWitupTest
 */

public class ParallelListResourceBundleWitupTest {

    public static void main(String[] args) {
        ParallelListResourceBundle bundle = new ParallelListResourceBundle() {
            @Override
            protected Object[][] getContents() {
                return new Object[0][];
            }
        };

        try {
            bundle.remove();
            throw new Error("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // Test passed
        }
    }
}
