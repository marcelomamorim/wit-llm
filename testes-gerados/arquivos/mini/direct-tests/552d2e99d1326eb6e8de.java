/*
 * @test
 * @summary Test java.security.Provider.elements() method behavior
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.util.Enumeration;

public class ProviderWitupTest extends Provider {

    public ProviderWitupTest() {
        super("TestProvider", 1.0, "Test Provider for elements() method");
    }

    public static void main(String[] args) {
        ProviderWitupTest provider = new ProviderWitupTest();

        // Add some properties to the provider
        provider.put("key1", "value1");
        provider.put("key2", "value2");

        Enumeration<Object> elements = provider.elements();

        boolean foundKey1 = false;
        boolean foundKey2 = false;

        while (elements.hasMoreElements()) {
            Object element = elements.nextElement();
            if ("value1".equals(element)) {
                foundKey1 = true;
            } else if ("value2".equals(element)) {
                foundKey2 = true;
            }
        }

        if (!foundKey1) {
            throw new RuntimeException("Expected value 'value1' not found in elements enumeration");
        }
        if (!foundKey2) {
            throw new RuntimeException("Expected value 'value2' not found in elements enumeration");
        }

        // Test that elements() returns an Enumeration with all values
        // Also test that elements() can be called multiple times and returns consistent results
        Enumeration<Object> elements2 = provider.elements();
        int count = 0;
        while (elements2.hasMoreElements()) {
            elements2.nextElement();
            count++;
        }
        if (count < 2) {
            throw new RuntimeException("Expected at least 2 elements in enumeration, found " + count);
        }

        System.out.println("Test passed: elements() returns expected values and enumeration behaves correctly.");
    }
}
