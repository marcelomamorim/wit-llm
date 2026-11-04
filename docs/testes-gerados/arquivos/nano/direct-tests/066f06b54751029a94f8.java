/* @test    @summary Test for DomainName.registeredDomain(String domain) method    @run main DomainNameWitupTest */

import sun.security.util.DomainName;
import sun.security.util.DomainName.RegisteredDomain;

public class DomainNameWitupTest {

    public static void main(String[] args) {
        testRegisteredDomainWithValidDomain();
        testRegisteredDomainWithNull();
        testRegisteredDomainWithEmptyString();
        testRegisteredDomainWithUnknownDomain();
    }

    private static void testRegisteredDomainWithValidDomain() {
        String domain = "oracle.com";
        RegisteredDomain result = DomainName.registeredDomain(domain);
        if (result == null) {
            throw new AssertionError("Expected non-null result for domain: " + domain);
        }
        String regDomain = result.registeredDomain();
        if (regDomain == null || !regDomain.equals("oracle.com")) {
            throw new AssertionError("Unexpected registered domain: " + regDomain);
        }
    }

    private static void testRegisteredDomainWithNull() {
        String domain = null;
        RegisteredDomain result = DomainName.registeredDomain(domain);
        if (result != null) {
            throw new AssertionError("Expected null result for null domain");
        }
    }

    private static void testRegisteredDomainWithEmptyString() {
        String domain = "";
        RegisteredDomain result = DomainName.registeredDomain(domain);
        if (result != null) {
            throw new AssertionError("Expected null result for empty domain");
        }
    }

    private static void testRegisteredDomainWithUnknownDomain() {
        String domain = "unknown.tld";
        RegisteredDomain result = DomainName.registeredDomain(domain);
        if (result != null) {
            throw new AssertionError("Expected null result for unknown domain: " + domain);
        }
    }
}
