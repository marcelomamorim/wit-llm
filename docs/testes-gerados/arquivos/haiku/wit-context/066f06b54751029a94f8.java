/*
 * @test
 * @summary Checks registeredDomain null handling and basic observable results of DomainName.registeredDomain
 * @modules java.base/sun.security.util
 * @run main DomainNameWitupTest
 */

import sun.security.util.DomainName;

public class DomainNameWitupTest {

    public static void main(String[] args) throws Exception {
        try {
            DomainName.registeredDomain(null);
            throw new RuntimeException("expected NullPointerException for null domain");
        } catch (NullPointerException expected) {
        }

        Object registered = DomainName.registeredDomain("oracle.com");
        if (registered == null) {
            throw new RuntimeException("expected a registered domain for oracle.com");
        }

        Object suffixOnly = DomainName.registeredDomain("com");
        if (suffixOnly != null) {
            throw new RuntimeException("expected null for public suffix without registrant label");
        }
    }
}
