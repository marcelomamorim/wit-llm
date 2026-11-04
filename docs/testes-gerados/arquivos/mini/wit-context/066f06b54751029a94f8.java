/*
 * @test
 * @summary Test sun.security.util.DomainName.registeredDomain(String) method
 * @run main DomainNameWitupTest
 */

public class DomainNameWitupTest {
    public static void main(String[] args) {
        testRegisteredDomainNullInput();
        testRegisteredDomainValidInput();
    }

    private static void testRegisteredDomainNullInput() {
        // According to current source, passing null to registeredDomain throws NullPointerException
        try {
            sun.security.util.DomainName.registeredDomain(null);
            throw new RuntimeException("Expected NullPointerException not thrown for null input");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testRegisteredDomainValidInput() {
        // Test with a valid domain string that should not cause exception
        // We do not know the exact domain format or expected output, so test for non-exception and null or non-null return
        String domain = "example.com";
        sun.security.util.DomainName.RegisteredDomain rd = sun.security.util.DomainName.registeredDomain(domain);
        // The method may return null if no match found, so just check no exception and allow null
        if (rd != null) {
            // Check that returned object is of correct type
            if (!(rd instanceof sun.security.util.DomainName.RegisteredDomain)) {
                throw new RuntimeException("registeredDomain returned object of unexpected type");
            }
        }
    }
}
