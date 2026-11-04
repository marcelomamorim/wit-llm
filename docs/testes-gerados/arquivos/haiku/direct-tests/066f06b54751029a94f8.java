/*
 * @test
 * @summary Checks public suffix and registered domain lookup in DomainName.registeredDomain
 * @modules java.base/sun.security.util
 * @run main DomainNameWitupTest
 */

import sun.security.util.DomainName;

public class DomainNameWitupTest {

    public static void main(String[] args) throws Exception {
        testRegisteredDomainForSubdomainReturnsNonNull();
        testRegisteredDomainForRegisteredDomainReturnsNonNull();
        testRegisteredDomainForPublicSuffixReturnsNull();
    }

    static void testRegisteredDomainForSubdomainReturnsNonNull() {
        Object result = DomainName.registeredDomain("www.oracle.com");
        if (result == null) {
            throw new RuntimeException("Expected registered domain for www.oracle.com");
        }
    }

    static void testRegisteredDomainForRegisteredDomainReturnsNonNull() {
        Object result = DomainName.registeredDomain("oracle.com");
        if (result == null) {
            throw new RuntimeException("Expected registered domain for oracle.com");
        }
    }

    static void testRegisteredDomainForPublicSuffixReturnsNull() {
        Object result = DomainName.registeredDomain("com");
        if (result != null) {
            throw new RuntimeException("Expected null registered domain for public suffix com");
        }
    }
}
