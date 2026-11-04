/* @test    @summary Test for AuthorityInfoAccessExtension.get(String) method to verify exception handling and normal behavior    @run main AuthorityInfoAccessExtensionWitupTest */

import sun.security.x509.AuthorityInfoAccessExtension;
import sun.security.util.DerValue;
import java.io.IOException;
import java.util.*;

public class AuthorityInfoAccessExtensionWitupTest {

    public static void main(String[] args) {
        testGetWithDescriptions();
        testGetWithInvalidName();
    }

    private static void testGetWithDescriptions() {
        try {
            List<AuthorityInfoAccessExtension.AccessDescription> descriptions = new ArrayList<>();
            AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(descriptions);
            List<AuthorityInfoAccessExtension.AccessDescription> result = ext.get("descriptions");
            if (result != null && result == descriptions) {
                System.out.println("PASS: get("descriptions") returns the correct list.");
            } else {
                System.out.println("FAIL: get("descriptions") did not return the expected list.");
            }
        } catch (IOException e) {
            System.out.println("FAIL: Exception thrown in testGetWithDescriptions: " + e);
        }
    }

    private static void testGetWithInvalidName() {
        try {
            List<AuthorityInfoAccessExtension.AccessDescription> descriptions = new ArrayList<>();
            AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(descriptions);
            ext.get("invalidName");
            System.out.println("FAIL: get("invalidName") did not throw IOException.");
        } catch (IOException e) {
            String expectedMessagePart = "not recognized";
            if (e.getMessage() != null && e.getMessage().contains(expectedMessagePart)) {
                System.out.println("PASS: get("invalidName") threw IOException as expected.");
            } else {
                System.out.println("FAIL: get("invalidName") threw IOException, but message was unexpected: " + e.getMessage());
            }
        }
    }
}
