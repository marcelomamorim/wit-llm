/*
 * @test
 * @summary Test sun.security.x509.AuthorityInfoAccessExtension.get(String) method
 * @run main AuthorityInfoAccessExtensionWitupTest
 */

import sun.security.x509.AuthorityInfoAccessExtension;
import sun.security.x509.AccessDescription;
import sun.security.x509.PKIXExtensions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthorityInfoAccessExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws IOException {
        List<AccessDescription> descs = new ArrayList<>();
        // AccessDescription has no public constructors visible here, so use empty list
        AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(descs);
        List<AccessDescription> result = ext.get("descriptions");
        if (result == null) {
            throw new RuntimeException("Returned list is null");
        }
        if (result != descs) {
            throw new RuntimeException("Returned list is not the same as the original list");
        }
    }

    private static void testGetWithInvalidName() {
        try {
            List<AccessDescription> descs = new ArrayList<>();
            AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(descs);
            ext.get("invalidName");
            throw new RuntimeException("Expected IOException not thrown for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
