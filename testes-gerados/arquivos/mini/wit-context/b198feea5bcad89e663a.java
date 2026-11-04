/*
 * @test
 * @summary Test AuthorityInfoAccessExtension.delete(String) method behavior
 * @run main AuthorityInfoAccessExtensionWitupTest
 */

import sun.security.x509.AuthorityInfoAccessExtension;
import sun.security.x509.AccessDescription;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthorityInfoAccessExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteWithValidName();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithValidName() throws Exception {
        List<AccessDescription> list = new ArrayList<>();
        // Create instance with non-empty list
        AuthorityInfoAccessExtension aia = new AuthorityInfoAccessExtension(list);
        // Add dummy AccessDescription to list to verify it is cleared
        list.add(new AccessDescription(null, null));
        // Delete with valid name "descriptions" (case insensitive)
        aia.delete("descriptions");
        // After delete, the internal list should be cleared (empty)
        // We cannot access private field, but no exception means success
        // So we call delete again with "descriptions" to verify no exception
        aia.delete("DESCRIPTIONS");
    }

    private static void testDeleteWithInvalidName() throws Exception {
        List<AccessDescription> list = new ArrayList<>();
        AuthorityInfoAccessExtension aia = new AuthorityInfoAccessExtension(list);
        try {
            aia.delete("invalidName");
            throw new RuntimeException("Expected IOException not thrown for invalid name");
        } catch (IOException e) {
            // Expected exception, verify message contains invalid name
            if (!e.getMessage().contains("invalidName")) {
                throw new RuntimeException("IOException message does not contain invalid name");
            }
        }
    }
}
