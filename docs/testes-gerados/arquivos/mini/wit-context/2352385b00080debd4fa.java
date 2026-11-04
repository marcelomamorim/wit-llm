/*
 * @test
 * @summary Test java.security.Provider.replace(Object key, Object value) for expected behavior and exception
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {
    public static void main(String[] args) {
        testReplaceThrowsWhenNotInitialized();
        testReplaceReturnsNullWhenKeyAbsent();
        testReplaceReturnsPreviousValueWhenPresent();
    }

    private static void testReplaceThrowsWhenNotInitialized() {
        // Create a minimal subclass to access protected constructor
        Provider p = new Provider("TestProvider", 1.0, "Test Provider") {
            private static final long serialVersionUID = 1L;
        };

        // The replace method calls check("putProviderProperty." + name) which throws IllegalStateException if not initialized
        // Since we do not initialize the provider, replace should throw IllegalStateException
        try {
            p.replace("key", "value");
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
    }

    private static void testReplaceReturnsNullWhenKeyAbsent() {
        Provider p = new Provider("TestProvider", 1.0, "Test Provider") {
            private static final long serialVersionUID = 1L;

            // Override implReplace to simulate behavior
            @Override
            protected synchronized Object implReplace(Object key, Object value) {
                // Simulate no previous value
                return null;
            }

            // Override check to do nothing to bypass initialization check
            @Override
            protected void check(String action) {
                // do nothing
            }
        };

        Object previous = p.replace("absentKey", "newValue");
        if (previous != null) {
            throw new RuntimeException("Expected null when replacing absent key");
        }
    }

    private static void testReplaceReturnsPreviousValueWhenPresent() {
        Provider p = new Provider("TestProvider", 1.0, "Test Provider") {
            private static final long serialVersionUID = 1L;

            private Object storedValue = "oldValue";

            @Override
            protected synchronized Object implReplace(Object key, Object value) {
                Object old = storedValue;
                storedValue = value;
                return old;
            }

            @Override
            protected void check(String action) {
                // do nothing
            }
        };

        Object previous = p.replace("someKey", "newValue");
        if (!"oldValue".equals(previous)) {
            throw new RuntimeException("Expected previous value 'oldValue', got: " + previous);
        }
    }
}
