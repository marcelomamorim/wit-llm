/*
 * @test
 * @summary Test java.security.Provider.forEach method for exception and normal behavior
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.util.function.BiConsumer;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testForEachThrowsIllegalStateExceptionIfNotInitialized();
        testForEachThrowsNullPointerExceptionIfActionNull();
        testForEachInvokesSuperForEach();
    }

    private static void testForEachThrowsIllegalStateExceptionIfNotInitialized() {
        Provider p = new Provider("TestProvider", 1.0, "Test Info") {
            private boolean initialized = false;
            @Override
            protected synchronized void checkInitialized() {
                if (!initialized) {
                    throw new IllegalStateException("Provider not initialized");
                }
            }
            @Override
            public synchronized void forEach(BiConsumer<? super Object, ? super Object> action) {
                checkInitialized();
                super.forEach(action);
            }
        };

        try {
            p.forEach((k, v) -> {});
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    private static void testForEachThrowsNullPointerExceptionIfActionNull() {
        Provider p = new Provider("TestProvider", 1.0, "Test Info") {
            private boolean initialized = true;
            @Override
            protected synchronized void checkInitialized() {
                if (!initialized) {
                    throw new IllegalStateException("Provider not initialized");
                }
            }
            @Override
            public synchronized void forEach(BiConsumer<? super Object, ? super Object> action) {
                checkInitialized();
                super.forEach(action);
            }
        };

        try {
            p.forEach(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testForEachInvokesSuperForEach() {
        final StringBuilder sb = new StringBuilder();
        Provider p = new Provider("TestProvider", 1.0, "Test Info") {
            private boolean initialized = true;
            @Override
            protected synchronized void checkInitialized() {
                if (!initialized) {
                    throw new IllegalStateException("Provider not initialized");
                }
            }
            @Override
            public synchronized void forEach(BiConsumer<? super Object, ? super Object> action) {
                checkInitialized();
                super.forEach(action);
            }
        };

        // Add a property to the Provider (which extends Properties) to test forEach
        p.put("key1", "value1");
        p.put("key2", "value2");

        p.forEach((k, v) -> sb.append(k).append("=").append(v).append(","));

        String result = sb.toString();
        if (!result.contains("key1=value1") || !result.contains("key2=value2")) {
            throw new RuntimeException("forEach did not iterate over all entries");
        }
    }
}
