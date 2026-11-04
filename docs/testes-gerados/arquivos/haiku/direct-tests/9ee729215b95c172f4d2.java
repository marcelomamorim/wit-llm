/*
 * @test
 * @summary Verify SubjectInfoAccessExtension.set name and value validation
 * @modules java.base/sun.security.x509
 * @run main SubjectInfoAccessExtensionWitupTest
 */

import java.io.IOException;
import java.util.ArrayList;
import sun.security.x509.AccessDescription;
import sun.security.x509.SubjectInfoAccessExtension;

public class SubjectInfoAccessExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        SubjectInfoAccessExtension ext =
            new SubjectInfoAccessExtension(new ArrayList<AccessDescription>());

        expectIOException(ext, "descriptions", "not a list");
        expectIOException(ext, "descriptions", null);
        expectIOException(ext, "unknown", new ArrayList<AccessDescription>());
        expectIOException(ext, "", new ArrayList<AccessDescription>());

        ext.set("descriptions", new ArrayList<AccessDescription>());
        ext.set("DESCRIPTIONS", new ArrayList<AccessDescription>());
        ext.set("Descriptions", new ArrayList<AccessDescription>());
    }

    private static void expectIOException(SubjectInfoAccessExtension ext,
                                          String name, Object value) {
        try {
            ext.set(name, value);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for name=" + name
                + " value=" + value);
    }
}
