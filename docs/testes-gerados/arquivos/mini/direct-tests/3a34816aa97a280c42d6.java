/*
 * @test
 * @summary Test SubjectInfoAccessExtension.get(String) method behavior
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
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws Exception {
        List<AccessDescription> list = new ArrayList<>();
        // Create a dummy AccessDescription with minimal valid data
        AccessDescription ad = new AccessDescription(PKIXExtensions.AuthorityInfoAccess_Id, null);
        list.add(ad);

        SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(list);

        List<AccessDescription> result = ext.get("descriptions");
        if (result == null) {
            throw new RuntimeException("Returned list is null");
        }
        if (result.size() != 1) {
            throw new RuntimeException("Returned list size expected 1 but was " + result.size());
        }
        if (result.get(0) != ad) {
            throw new RuntimeException("Returned list element does not match the added AccessDescription");
        }
    }

    private static void testGetWithInvalidName() {
        try {
            SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(new ArrayList<AccessDescription>());
            ext.get("invalidName");
            throw new RuntimeException("Expected IOException was not thrown for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
