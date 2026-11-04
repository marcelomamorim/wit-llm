/*
 * @test
 * @summary Checks IOException behavior of AuthorityInfoAccessExtension.set for invalid names and value types
 * @modules java.base/sun.security.x509
 * @run main AuthorityInfoAccessExtensionWitupTest
 */

import java.io.IOException;
import java.util.ArrayList;

import sun.security.x509.AuthorityInfoAccessExtension;

public class AuthorityInfoAccessExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        AuthorityInfoAccessExtension ext =
                new AuthorityInfoAccessExtension(new ArrayList<>());

        testUnknownNameThrows(ext);
        testDescriptionsWithNonListThrows(ext);
        testDescriptionsWithNullThrows(ext);
        testDescriptionsIgnoreCaseAccepted(ext);
    }

    private static void testUnknownNameThrows(AuthorityInfoAccessExtension ext) {
        try {
            ext.set("unknownName", new ArrayList<>());
            throw new RuntimeException("Expected IOException for unknown attribute name");
        } catch (IOException e) {
            if (e.getMessage() == null || !e.getMessage().contains("not recognized")) {
                throw new RuntimeException("Unexpected message: " + e.getMessage(), e);
            }
        }
    }

    private static void testDescriptionsWithNonListThrows(AuthorityInfoAccessExtension ext) {
        try {
            ext.set("descriptions", "not a list");
            throw new RuntimeException("Expected IOException for non-List value");
        } catch (IOException e) {
            if (e.getMessage() == null || !e.getMessage().contains("type List")) {
                throw new RuntimeException("Unexpected message: " + e.getMessage(), e);
            }
        }
    }

    private static void testDescriptionsWithNullThrows(AuthorityInfoAccessExtension ext) {
        try {
            ext.set("descriptions", null);
            throw new RuntimeException("Expected IOException for null value");
        } catch (IOException e) {
            if (e.getMessage() == null || !e.getMessage().contains("type List")) {
                throw new RuntimeException("Unexpected message: " + e.getMessage(), e);
            }
        }
    }

    private static void testDescriptionsIgnoreCaseAccepted(AuthorityInfoAccessExtension ext)
            throws IOException {
        ext.set("DESCRIPTIONS", new ArrayList<>());
    }
}
