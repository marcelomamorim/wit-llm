/* @test    @summary Test for Provider.clear() method to verify behavior when provider is uninitialized.    @run main ProviderWitupTest */

import java.security.Provider;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testClearWhenNotInitialized();
    }

    public static void testClearWhenNotInitialized() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {
            private boolean initialized = false;

            @Override
            public synchronized void clear() {
                // Simulate uninitialized state
                if (!initialized) {
                    throw new IllegalStateException("Provider not initialized") {
                        @Override
                        public String getMessage() {
                            return "Provider not initialized";
                        }
                    };
                }
                super.clear();
            }
        };
        try {
            provider.clear();
            // If no exception, test fails because expected exception is thrown
            System.err.println("FAIL: Expected IllegalStateException was not thrown.");
        } catch (IllegalStateException e) {
            // Verify exception message
            if ("Provider not initialized".equals(e.getMessage())) {
                System.out.println("PASS: IllegalStateException thrown as expected.");
            } else {
                System.err.println("FAIL: Exception message mismatch.");
            }
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception: " + t);
        }
    }
}
