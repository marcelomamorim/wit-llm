/* @test    @summary Test AuthorityInfoAccessExtension.set method for valid and invalid inputs    @run main AuthorityInfoAccessExtensionWitupTest */

import sun.security.x509.AuthorityInfoAccessExtension;
import sun.security.x509.AccessDescription;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthorityInfoAccessExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetWithValidList();
        testSetWithInvalidName();
        testSetWithInvalidObject();
    }

    static void testSetWithValidList() throws IOException {
        List<AccessDescription> list = new ArrayList<>();
        // Add a dummy AccessDescription object (assuming constructor exists)
        list.add(new AccessDescription());
        AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(list);
        ext.set("descriptions", list);
        // No exception expected, verify internal state if accessible (not in this test)
    }

    static void testSetWithInvalidName() {
        List<AccessDescription> list = new ArrayList<>();
        try {
            AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(list);
            ext.set("invalidName", list);
            // Expect IOException
            throw new AssertionError("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            // Expected exception
        }
    }

    static void testSetWithInvalidObject() throws IOException {
        List<AccessDescription> list = new ArrayList<>();
        try {
            AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(list);
            ext.set("descriptions", "not a list");
            throw new AssertionError("Expected IOException for invalid object type");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
