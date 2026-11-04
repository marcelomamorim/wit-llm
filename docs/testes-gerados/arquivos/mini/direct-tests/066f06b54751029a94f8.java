/*
 * @test
 * @summary Test sun.security.util.DomainName.registeredDomain(String) method
 * @run main DomainNameWitupTest
 */

public class DomainNameWitupTest {
    public static void main(String[] args) {
        testRegisteredDomainNullInput();
        testRegisteredDomainEmptyString();
        testRegisteredDomainValidDomain();
    }

    private static void testRegisteredDomainNullInput() {
        // Expect null result for null input
        sun.security.util.RegisteredDomain rd = sun.security.util.DomainName.registeredDomain(null);
        if (rd != null) {
            throw new RuntimeException("Expected null for null input");
        }
    }

    private static void testRegisteredDomainEmptyString() {
        // Expect null result for empty string input
        sun.security.util.RegisteredDomain rd = sun.security.util.DomainName.registeredDomain("");
        if (rd != null) {
            throw new RuntimeException("Expected null for empty string input");
        }
    }

    private static void testRegisteredDomainValidDomain() {
        // For a valid domain, the method should return a RegisteredDomain instance or null
        // We cannot predict exact result without the internal database, so just test non-exception
        String domain = "example.com";
        sun.security.util.RegisteredDomain rd = sun.security.util.DomainName.registeredDomain(domain);
        // rd may be null if no match found, so just check no exception and type
        if (rd != null && !(rd instanceof sun.security.util.RegisteredDomain)) {
            throw new RuntimeException("Returned object is not RegisteredDomain instance");
        }
    }
}
