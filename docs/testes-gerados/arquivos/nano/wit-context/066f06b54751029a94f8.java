/*
 * @test
 * @summary Test for sun.security.util.DomainName.registeredDomain(String)
 * @run main DomainNameWitupTest
 */

import sun.security.util.DomainName;

public class DomainNameWitupTest {

    public static void main(String[] args) {
        testRegisteredDomainWithNull();
        testRegisteredDomainWithValidDomain();
        testRegisteredDomainWithEmptyString();
    }

    private static void testRegisteredDomainWithNull() {
        try {
            DomainName.registeredDomain(null);
            System.out.println("testRegisteredDomainWithNull: FAILED - Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testRegisteredDomainWithNull: PASSED");
        } catch (Throwable t) {
            System.out.println("testRegisteredDomainWithNull: FAILED - Unexpected exception: " + t);
        }
    }

    private static void testRegisteredDomainWithValidDomain() {
        String domain = "example.com";
        try {
            Object result = DomainName.registeredDomain(domain);
            if (result != null) {
                System.out.println("testRegisteredDomainWithValidDomain: PASSED");
            } else {
                System.out.println("testRegisteredDomainWithValidDomain: FAILED - Result is null");
            }
        } catch (Throwable t) {
            System.out.println("testRegisteredDomainWithValidDomain: FAILED - Exception: " + t);
        }
    }

    private static void testRegisteredDomainWithEmptyString() {
        String domain = "";
        try {
            Object result = DomainName.registeredDomain(domain);
            if (result == null) {
                System.out.println("testRegisteredDomainWithEmptyString: PASSED");
            } else {
                System.out.println("testRegisteredDomainWithEmptyString: FAILED - Result is not null");
            }
        } catch (Throwable t) {
            System.out.println("testRegisteredDomainWithEmptyString: FAILED - Exception: " + t);
        }
    }
}
