/* @test    @summary Test delete method of InhibitAnyPolicyExtension for exception paths    @run main InhibitAnyPolicyExtensionWitupTest */

import sun.security.x509.InhibitAnyPolicyExtension;
import java.io.IOException;

public class InhibitAnyPolicyExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithSkipCerts();
        testDeleteWithUnrecognizedName();
    }

    private static void testDeleteWithSkipCerts() {
        try {
            InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(0);
            ext.delete("skip_certs");
            System.out.println("testDeleteWithSkipCerts: Failed - no exception thrown");
        } catch (IOException e) {
            if (e.getMessage().contains("may not be deleted")) {
                System.out.println("testDeleteWithSkipCerts: Passed");
            } else {
                System.out.println("testDeleteWithSkipCerts: Failed - wrong message: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("testDeleteWithSkipCerts: Failed - unexpected exception: " + e);
        }
    }

    private static void testDeleteWithUnrecognizedName() {
        try {
            InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(0);
            ext.delete("unknown_attribute");
            System.out.println("testDeleteWithUnrecognizedName: Failed - no exception thrown");
        } catch (IOException e) {
            if (e.getMessage().contains("Attribute name not recognized")) {
                System.out.println("testDeleteWithUnrecognizedName: Passed");
            } else {
                System.out.println("testDeleteWithUnrecognizedName: Failed - wrong message: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("testDeleteWithUnrecognizedName: Failed - unexpected exception: " + e);
        }
    }
}
