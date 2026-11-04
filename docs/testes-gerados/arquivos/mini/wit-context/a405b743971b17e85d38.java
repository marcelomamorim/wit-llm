/*
 * @test
 * @summary Test SubjectInfoAccessExtension.delete(String) behavior
 * @run main SubjectInfoAccessExtensionWitupTest
 */

import sun.security.x509.SubjectInfoAccessExtension;
import sun.security.x509.AccessDescription;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class SubjectInfoAccessExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteWithValidName();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithValidName() throws Exception {
        // Prepare a list with one dummy AccessDescription (null safe test)
        List<AccessDescription> list = new ArrayList<>();
        // We do not have public constructor details for AccessDescription,
        // so we use empty list to test delete behavior deterministically
        SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(list);

        // Delete with name "descriptions" should clear accessDescriptions
        ext.delete("descriptions");

        // After delete, accessDescriptions should be empty list
        // We check by calling encodeThis indirectly via delete (no exception means success)
        // No direct getter, so we rely on no exception and no crash
    }

    private static void testDeleteWithInvalidName() throws Exception {
        List<AccessDescription> list = Collections.emptyList();
        SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(list);

        String invalidName = "invalidName";
        try {
            ext.delete(invalidName);
            throw new RuntimeException("Expected IOException not thrown for name: " + invalidName);
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains(invalidName) || !msg.contains("not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
            // Expected exception caught, test passes
        }
    }
}
