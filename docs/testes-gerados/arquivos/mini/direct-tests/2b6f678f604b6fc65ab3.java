/*
 * @test
 * @summary Test ModulePatcher.getURL() method behavior
 * @run main ModulePatcherWitupTest
 */

import java.net.URL;

public class ModulePatcherWitupTest {

    public static void main(String[] args) {
        ModulePatcherWitupTest test = new ModulePatcherWitupTest();
        test.testGetURL();
    }

    private void testGetURL() {
        ModulePatcher patcher = new ModulePatcher(null) {
            // Override shouldNotGetHere to throw an AssertionError to detect call
            @SuppressWarnings("unchecked")
            private <T> T shouldNotGetHere(Class<T> clazz) {
                throw new AssertionError("shouldNotGetHere called with " + clazz.getName());
            }

            @Override
            public URL getURL() {
                return shouldNotGetHere(URL.class);
            }
        };

        try {
            patcher.getURL();
            throw new RuntimeException("Expected AssertionError not thrown");
        } catch (AssertionError e) {
            String msg = e.getMessage();
            if (!msg.contains("shouldNotGetHere called with java.net.URL")) {
                throw new RuntimeException("Unexpected AssertionError message: " + msg);
            }
        }
    }
}
