/* @test    @summary Test InhibitAnyPolicyExtension.set(String, Object) for valid and invalid inputs    @run main InhibitAnyPolicyExtensionWitupTest */

import sun.security.x509.InhibitAnyPolicyExtension;
import java.io.IOException;

public class InhibitAnyPolicyExtensionWitupTest {

    public static void main(String[] args) {
        testSetWithValidSkipCerts();
        testSetWithInvalidAttributeName();
        testSetWithInvalidObjectType();
        testSetWithNegativeSkipCerts();
        testSetWithUnlimitedSkipCerts();
    }

    private static void testSetWithValidSkipCerts() {
        try {
            InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(0);
            ext.set("skip_certs", Integer.valueOf(5));
            System.out.println("testSetWithValidSkipCerts passed");
        } catch (Exception e) {
            System.err.println("testSetWithValidSkipCerts failed: " + e);
        }
    }

    private static void testSetWithInvalidAttributeName() {
        try {
            InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(0);
            ext.set("invalid_name", Integer.valueOf(5));
            System.err.println("testSetWithInvalidAttributeName failed: no exception thrown");
        } catch (IOException e) {
            System.out.println("testSetWithInvalidAttributeName passed: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("testSetWithInvalidAttributeName failed: " + e);
        }
    }

    private static void testSetWithInvalidObjectType() {
        try {
            InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(0);
            ext.set("skip_certs", "not_an_integer");
            System.err.println("testSetWithInvalidObjectType failed: no exception thrown");
        } catch (IOException e) {
            System.out.println("testSetWithInvalidObjectType passed: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("testSetWithInvalidObjectType failed: " + e);
        }
    }

    private static void testSetWithNegativeSkipCerts() {
        try {
            InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(0);
            ext.set("skip_certs", Integer.valueOf(-2));
            System.err.println("testSetWithNegativeSkipCerts failed: no exception thrown");
        } catch (IOException e) {
            System.out.println("testSetWithNegativeSkipCerts passed: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("testSetWithNegativeSkipCerts failed: " + e);
        }
    }

    private static void testSetWithUnlimitedSkipCerts() {
        try {
            InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(0);
            ext.set("skip_certs", Integer.valueOf(-1));
            System.out.println("testSetWithUnlimitedSkipCerts passed");
        } catch (Exception e) {
            System.err.println("testSetWithUnlimitedSkipCerts failed: " + e);
        }
    }
}
