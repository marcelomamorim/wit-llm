/* @test    @summary Test for SubjectInfoAccessExtension.set method handling valid and invalid inputs    @run main SubjectInfoAccessExtensionWitupTest */

import java.io.IOException;
import java.util.*;
import sun.security.x509.SubjectInfoAccessExtension;

public class SubjectInfoAccessExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetWithValidDescriptions();
        testSetWithInvalidName();
    }

    private static void testSetWithValidDescriptions() throws IOException {
        List<AccessDescription> descriptions = new ArrayList<>();
        SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(descriptions);
        ext.set("descriptions", descriptions);
        System.out.println("testSetWithValidDescriptions passed");
    }

    private static void testSetWithInvalidName() {
        SubjectInfoAccessExtension ext = null;
        try {
            ext = new SubjectInfoAccessExtension(Collections.emptyList());
        } catch (IOException e) {
            // Should not happen with empty list
            e.printStackTrace();
            return;
        }
        try {
            ext.set("invalidName", new Object());
            System.err.println("testSetWithInvalidName failed: no exception thrown");
        } catch (IOException e) {
            String message = e.getMessage();
            if (message != null && message.contains("not recognized")) {
                System.out.println("testSetWithInvalidName passed");
            } else {
                System.err.println("testSetWithInvalidName failed: unexpected message: " + message);
            }
        }
    }

    // Dummy class to satisfy type; in real tests, use actual AccessDescription instances
    static class AccessDescription {
        // no implementation needed for this test
    }
}
