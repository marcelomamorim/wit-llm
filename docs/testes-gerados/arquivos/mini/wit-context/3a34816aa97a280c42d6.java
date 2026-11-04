/*
 * @test
 * @summary Test SubjectInfoAccessExtension.get(String) method behavior
 * @run main SubjectInfoAccessExtensionWitupTest
 */

import sun.security.x509.SubjectInfoAccessExtension;
import sun.security.x509.AccessDescription;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SubjectInfoAccessExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws IOException {
        List<AccessDescription> list = new ArrayList<>();
        // We do not add any AccessDescription because construction is enough to test get behavior
        SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(list);
        List<AccessDescription> result = ext.get("descriptions");
        if (result == null) {
            throw new RuntimeException("get returned null for valid name 'descriptions'");
        }
        if (result != list) {
            throw new RuntimeException("get did not return the original list instance");
        }
    }

    private static void testGetWithInvalidName() {
        List<AccessDescription> list = new ArrayList<>();
        try {
            SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(list);
            ext.get("invalidName");
            throw new RuntimeException("Expected IOException not thrown for invalid name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
