/*
 * @test
 * @summary Verify SubjectInfoAccessExtension.delete accepts the descriptions attribute case-insensitively and rejects other names with IOException
 * @modules java.base/sun.security.x509
 * @run main SubjectInfoAccessExtensionWitupTest
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import sun.security.x509.AccessDescription;
import sun.security.x509.SubjectInfoAccessExtension;

public class SubjectInfoAccessExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        List<AccessDescription> empty = new ArrayList<AccessDescription>();
        SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(empty);

        ext.delete("descriptions");
        ext.delete("DESCRIPTIONS");
        ext.delete("Descriptions");

        expectIOException(ext, "unknown");
        expectIOException(ext, "");
        expectIOException(ext, "descriptions2");
        expectIOException(ext, "SubjectInfoAccess");

        ext.delete("descriptions");
    }

    private static void expectIOException(SubjectInfoAccessExtension ext,
            String name) throws Exception {
        boolean thrown = false;
        try {
            ext.delete(name);
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for name: ["
                    + name + "]");
        }
    }
}
