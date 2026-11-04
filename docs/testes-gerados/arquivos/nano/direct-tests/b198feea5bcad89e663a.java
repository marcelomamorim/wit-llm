/* @test    @summary Test delete method of AuthorityInfoAccessExtension    @run main AuthorityInfoAccessExtensionWitupTest */

import java.io.IOException;
import java.util.*;
import sun.security.x509.AuthorityInfoAccessExtension;

public class AuthorityInfoAccessExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithDescriptions();
        testDeleteWithInvalidName();
    }

    static void testDeleteWithDescriptions() throws IOException {
        List<AuthorityInfoAccessExtension.AccessDescription> list = new ArrayList<>();
        AuthorityInfoAccessExtension.AccessDescription ad = null;
        try {
            ad = new AuthorityInfoAccessExtension.AccessDescription();
        } catch (Exception e) {
            // ignore, as constructor is not shown
        }
        list.add(ad);
        AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(list);
        ext.delete("descriptions");
        if (ext.accessDescriptions.size() != 0) {
            throw new AssertionError("Expected empty accessDescriptions after delete");
        }
        ext.delete("descriptions"); // should not throw
    }

    static void testDeleteWithInvalidName() {
        List<AuthorityInfoAccessExtension.AccessDescription> list = new ArrayList<>();
        AuthorityInfoAccessExtension ext = null;
        try {
            ext = new AuthorityInfoAccessExtension(list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        boolean thrown = false;
        try {
            ext.delete("invalidName");
        } catch (IOException e) {
            thrown = true;
            if (!e.getMessage().contains("not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new AssertionError("Expected IOException for invalid attribute name");
        }
    }
}
