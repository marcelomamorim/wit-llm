/*
 * @test
 * @summary Test SubjectInfoAccessExtension.delete(String) method behavior
 * @run main SubjectInfoAccessExtensionWitupTest
 */

import sun.security.x509.SubjectInfoAccessExtension;
import sun.security.x509.AccessDescription;
import sun.security.x509.PKIXExtensions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SubjectInfoAccessExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteDescriptions();
        testDeleteInvalidName();
    }

    private static void testDeleteDescriptions() throws Exception {
        List<AccessDescription> list = new ArrayList<>();
        // Add one dummy AccessDescription to list
        list.add(new AccessDescription(PKIXExtensions.AuthorityInfoAccess_Id, null));

        SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(list);

        // Delete with "descriptions" (case insensitive)
        ext.delete("descriptions");

        // After delete, the accessDescriptions list should be empty
        // We check by encoding and verifying no exception is thrown
        ext.encode(); // encode() is public in Extension superclass

        // Also test with uppercase
        ext.delete("DESCRIPTIONS");
        ext.encode();
    }

    private static void testDeleteInvalidName() {
        try {
            List<AccessDescription> list = new ArrayList<>();
            SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(list);
            ext.delete("invalidName");
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not recognized")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
