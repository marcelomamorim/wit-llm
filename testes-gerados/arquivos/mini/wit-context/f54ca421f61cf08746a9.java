/*
 * @test
 * @summary Test java.security.Provider.getAttribute(String) for null argument and normal behavior
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {
    public static void main(String[] args) {
        // Create a minimal concrete subclass of Provider since Provider is abstract
        Provider p = new Provider("TestProvider", 1.0, "Test Provider Info") {
            private static final long serialVersionUID = 1L;
        };

        // Test 1: Passing null should throw NullPointerException
        try {
            p.getAttribute(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }

        // Test 2: Passing a non-null string returns null (default behavior as no attributes set)
        String result = p.getAttribute("anyName");
        if (result != null) {
            throw new RuntimeException("Expected null for attribute not set, got: " + result);
        }

        System.out.println("All tests passed.");
    }
}
