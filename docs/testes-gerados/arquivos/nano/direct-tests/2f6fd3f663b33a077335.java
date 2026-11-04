/* @test    @summary Test AuthorityInfoAccessExtension.get(String) method for valid and invalid attribute names    @run main AuthorityInfoAccessExtensionWitupTest */

import sun.security.x509.AuthorityInfoAccessExtension;
import sun.security.x509.AccessDescription;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthorityInfoAccessExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws IOException {
        List<AccessDescription> descriptions = new ArrayList<>();
        // Add a dummy AccessDescription object (assuming constructor exists)
        AccessDescription ad = new AccessDescription();
        descriptions.add(ad);
        AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(descriptions);
        List<AccessDescription> result = ext.get(AuthorityInfoAccessExtension.DESCRIPTIONS);
        if (result != null && result.size() == 1 && result.get(0) == ad) {
            System.out.println("testGetWithValidName passed");
        } else {
            throw new AssertionError("testGetWithValidName failed");
        }
    }

    private static void testGetWithInvalidName() {
        List<AccessDescription> descriptions = new ArrayList<>();
        try {
            AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(descriptions);
            ext.get("InvalidName");
            throw new AssertionError("Expected IOException not thrown");
        } catch (IOException e) {
            String expectedMessage = "Attribute name [" + "InvalidName" + "] not recognized by " + "CertAttrSet:AuthorityInfoAccessExtension.";
            if (e.getMessage().contains("not recognized")) {
                System.out.println("testGetWithInvalidName passed");
            } else {
                throw new AssertionError("testGetWithInvalidName failed: " + e.getMessage());
            }
        }
    }
}
