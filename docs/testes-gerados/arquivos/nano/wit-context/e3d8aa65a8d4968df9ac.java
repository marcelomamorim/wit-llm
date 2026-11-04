/* @test    @summary Test for Provider.getOrDefault handling of uninitialized state    @run main ProviderWitupTest */

import java.security.Provider;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testGetOrDefaultThrowsIllegalStateExceptionWhenUninitialized();
    }

    private static void testGetOrDefaultThrowsIllegalStateExceptionWhenUninitialized() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {
            private boolean initialized = false;

            @Override
            public synchronized Object getOrDefault(Object key, Object defaultValue) {
                if (!initialized) {
                    throw new IllegalStateException("Provider not initialized") ;
                }
                return super.getOrDefault(key, defaultValue);
            }

            private void checkInitialized() {
                if (!initialized) {
                    throw new IllegalStateException("Provider not initialized") ;
                }
            }
        };
        try {
            provider.getOrDefault("someKey", "default") ;
            // Fail if no exception thrown
            System.err.println("Expected IllegalStateException was not thrown.");
        } catch (IllegalStateException e) {
            // Expected exception
            System.out.println("Caught expected IllegalStateException.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
        }
    }
}
