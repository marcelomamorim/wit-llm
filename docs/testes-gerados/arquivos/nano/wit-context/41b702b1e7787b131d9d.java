/* @test    @summary Test for AuthorityInfoAccessExtension.set(String, Object) handling valid and invalid inputs    @run main AuthorityInfoAccessExtensionWitupTest */

import sun.security.x509.AuthorityInfoAccessExtension;
import java.io.IOException;
import java.util.*;

public class AuthorityInfoAccessExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetWithValidDescriptions();
        testSetWithInvalidName();
    }

    private static void testSetWithValidDescriptions() throws IOException {
        List<AuthorityInfoAccessExtension.AccessDescription> descriptions = new ArrayList<>();
        AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(descriptions);
        // Prepare a valid List object
        List<AuthorityInfoAccessExtension.AccessDescription> newDescriptions = new ArrayList<>();
        ext.set("descriptions", newDescriptions);
        System.out.println("testSetWithValidDescriptions passed.");
    }

    private static void testSetWithInvalidName() {
        AuthorityInfoAccessExtension ext = null;
        try {
            ext = new AuthorityInfoAccessExtension(new ArrayList<>());
        } catch (IOException e) {
            // Should not happen in this test
            e.printStackTrace();
            return;
        }
        try {
            ext.set("invalidName", new Object());
            System.out.println("ERROR: Exception expected but not thrown.");
        } catch (IOException e) {
            if (e.getMessage().contains("not recognized")) {
                System.out.println("testSetWithInvalidName passed.");
            } else {
                System.out.println("Unexpected IOException message: " + e.getMessage());
            }
        }
    }
}
