/* @test    @summary Test for Provider.forEach method to verify exception paths and behavior.    @run main ProviderWitupTest */

import java.security.Provider;
import java.util.function.BiConsumer;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testForEachWithUninitializedProvider();
        testForEachWithNullAction();
    }

    private static void testForEachWithUninitializedProvider() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {
            private boolean initialized = false;
            private void checkInitialized() {
                if (!initialized) {
                    throw new IllegalStateException("Provider not initialized")
                }
            }
            @Override
            public synchronized void forEach(BiConsumer<? super Object, ? super Object> action) {
                checkInitialized();
                super.forEach(action);
            }
        };
        try {
            provider.forEach((k, v) -> {});
            // Expecting IllegalStateException due to uninitialized provider
            System.err.println("Failed: Expected IllegalStateException was not thrown.");
        } catch (IllegalStateException e) {
            System.out.println("Passed: Caught expected IllegalStateException.");
        } catch (Throwable t) {
            System.err.println("Failed: Caught unexpected exception: " + t);
        }
    }

    private static void testForEachWithNullAction() {
        Provider provider = new Provider("TestProvider", "1.0", "Test Info") {
            private boolean initialized = true;
            private void checkInitialized() {
                if (!initialized) {
                    throw new IllegalStateException("Provider not initialized")
                }
            }
            @Override
            public synchronized void forEach(BiConsumer<? super Object, ? super Object> action) {
                checkInitialized();
                super.forEach(action);
            }
        };
        try {
            provider.forEach(null);
            System.err.println("Failed: Expected NullPointerException was not thrown.");
        } catch (NullPointerException e) {
            System.out.println("Passed: Caught expected NullPointerException.");
        } catch (Throwable t) {
            System.err.println("Failed: Caught unexpected exception: " + t);
        }
    }
}
