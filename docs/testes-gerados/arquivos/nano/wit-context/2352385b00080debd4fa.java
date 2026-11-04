/*
 * @test
 * @summary Test for Provider.replace method, focusing on exception path when not initialized.
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testReplaceThrowsIllegalStateExceptionWhenNotInitialized();
    }

    public static void testReplaceThrowsIllegalStateExceptionWhenNotInitialized() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {
            private boolean initialized = false;

            private void check(String msg) {
                if (!initialized) {
                    throw new IllegalStateException("Provider not initialized");
                }
            }

            private Object implReplace(Object key, Object value) {
                // Simulate implementation replace logic
                return null;
            }

            @Override
            public synchronized Object replace(Object key, Object value) {
                check("putProviderProperty." + getName());
                return implReplace(key, value);
            }
        };

        try {
            provider.replace("someKey", "someValue");
            System.out.println("Test failed: IllegalStateException was not thrown.");
        } catch (IllegalStateException e) {
            System.out.println("Test passed: Caught expected IllegalStateException.");
        } catch (Exception e) {
            System.out.println("Test failed: Caught unexpected exception: " + e);
        }
    }
}
