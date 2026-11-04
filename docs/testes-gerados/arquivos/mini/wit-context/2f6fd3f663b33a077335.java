/*
 * @test
 * @summary Test sun.security.x509.AuthorityInfoAccessExtension.get(String) method
 * @run main AuthorityInfoAccessExtensionWitupTest
 */

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

    private static void testGetWithValidName() throws Exception {
        List<AccessDescription> descList = new ArrayList<>();
        AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(descList);
        List<AccessDescription> result = ext.get("descriptions");
        if (result == null) {
            throw new RuntimeException("Returned list should not be null for 'descriptions'");
        }
        if (result != descList) {
            throw new RuntimeException("Returned list should be the same instance as passed in constructor");
        }
    }

    private static void testGetWithInvalidName() {
        try {
            List<AccessDescription> descList = new ArrayList<>();
            AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(descList);
            ext.get("invalidName");
            throw new RuntimeException("Expected IOException not thrown for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type thrown: " + e);
        }
    }
}
