/*
 * @test
 * @summary Test the elements() method of java.security.Provider
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.util.Enumeration;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testElementsReturnsEnumeration();
    }

    public static void testElementsReturnsEnumeration() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {
            @Override
            public Enumeration<Object> elements() {
                return super.elements();
            }
        };
        Enumeration<Object> enumeration = provider.elements();
        if (enumeration == null) {
            throw new AssertionError("elements() returned null");
        }
        // Optionally, iterate over enumeration to verify it yields objects
        while (enumeration.hasMoreElements()) {
            Object obj = enumeration.nextElement();
            if (obj == null) {
                throw new AssertionError("Enumeration contains null element");
            }
        }
    }
}
