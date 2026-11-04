/*
 * @test
 * @summary Test java.security.Provider.elements() method behavior including IllegalStateException when uninitialized
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.util.Enumeration;

public class ProviderWitupTest {
    public static void main(String[] args) {
        testElementsThrowsIfNotInitialized();
        testElementsReturnsEnumerationWhenInitialized();
    }

    static void testElementsThrowsIfNotInitialized() {
        // Create a subclass to expose protected constructor and override elements to call super.elements()
        Provider p = new Provider("TestProvider", 1.0, "Test Provider") {
            private static final long serialVersionUID = 1L;
        };

        // The Provider constructor does not initialize the internal state fully for elements()
        // According to source, elements() calls checkInitialized() which throws IllegalStateException if not initialized
        try {
            p.elements();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
    }

    static void testElementsReturnsEnumerationWhenInitialized() {
        // Create a subclass of Provider that overrides checkInitialized to do nothing (simulate initialized)
        Provider p = new Provider("TestProvider", 1.0, "Test Provider") {
            private static final long serialVersionUID = 1L;

            // Override checkInitialized to do nothing to simulate initialized state
            void checkInitialized() {
                // no-op
            }

            // Override elements to call super.elements() which will call our no-op checkInitialized
            public Enumeration<Object> elements() {
                return super.elements();
            }
        };

        Enumeration<Object> e = p.elements();
        if (e == null) {
            throw new RuntimeException("elements() returned null Enumeration");
        }
        // The enumeration should be empty since no elements added
        if (e.hasMoreElements()) {
            throw new RuntimeException("elements() Enumeration should be empty");
        }
    }
}