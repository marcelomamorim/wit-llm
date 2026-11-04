/* @test    @summary Test delete method of SubjectInfoAccessExtension for known attribute name    @run main SubjectInfoAccessExtensionWitupTest */

import java.io.IOException;
import java.util.*;
import sun.security.x509.SubjectInfoAccessExtension;

public class SubjectInfoAccessExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithValidName();
        testDeleteWithInvalidName();
    }

    static void testDeleteWithValidName() throws IOException {
        List<sun.security.x509.AccessDescription> descriptions = new ArrayList<>();
        SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(descriptions);
        ext.delete("descriptions");
        if (!ext.accessDescriptions.isEmpty()) {
            throw new AssertionError("Expected accessDescriptions to be empty after delete") ;
        }
    }

    static void testDeleteWithInvalidName() {
        List<sun.security.x509.AccessDescription> descriptions = new ArrayList<>();
        try {
            SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(descriptions);
            ext.delete("invalidName");
            throw new AssertionError("Expected IOException for invalid attribute name") ;
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not recognized")) {
                throw new AssertionError("Unexpected exception message: " + msg, e);
            }
        }
    }
}
