/*
 * @test
 * @summary Verify SubjectInfoAccessExtension.delete accepts the descriptions attribute case-insensitively and rejects unknown names with IOException
 * @modules java.base/sun.security.x509
 * @run main SubjectInfoAccessExtensionWitupTest
 */

import java.io.IOException;
import java.util.ArrayList;

import sun.security.x509.SubjectInfoAccessExtension;

public class SubjectInfoAccessExtensionWitupTest {

    private static void checkDeleteAccepted(String name) throws IOException {
        SubjectInfoAccessExtension ext =
                new SubjectInfoAccessExtension(new ArrayList<>());
        ext.delete(name);
    }

    private static void checkDeleteRejected(String name) throws IOException {
        SubjectInfoAccessExtension ext =
                new SubjectInfoAccessExtension(new ArrayList<>());
        try {
            ext.delete(name);
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not recognized")) {
                throw new RuntimeException("Unexpected message for " + name + ": " + msg);
            }
            return;
        }
        throw new RuntimeException("Expected IOException for name: [" + name + "]");
    }

    public static void main(String[] args) throws Exception {
        checkDeleteAccepted(SubjectInfoAccessExtension.DESCRIPTIONS);
        checkDeleteAccepted("DESCRIPTIONS");
        checkDeleteAccepted("Descriptions");

        checkDeleteRejected("unknown");
        checkDeleteRejected("");
        checkDeleteRejected(SubjectInfoAccessExtension.NAME);
        checkDeleteRejected("descriptions ");
    }
}
