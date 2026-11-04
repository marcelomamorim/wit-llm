/*
 * @test
 * @summary Verifies public set(String, Object) behavior of SubjectInfoAccessExtension for valid and invalid attribute names and values
 * @modules java.base/sun.security.x509
 * @run main SubjectInfoAccessExtensionWitupTest
 */

import java.io.IOException;
import java.util.ArrayList;
import sun.security.x509.SubjectInfoAccessExtension;

public class SubjectInfoAccessExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        SubjectInfoAccessExtension ext =
                new SubjectInfoAccessExtension(new ArrayList<>());

        ext.set(SubjectInfoAccessExtension.DESCRIPTIONS, new ArrayList<>());
        ext.set("DESCRIPTIONS", new ArrayList<>());

        expectIOException(ext, "other", new ArrayList<>());
        expectIOException(ext, SubjectInfoAccessExtension.DESCRIPTIONS, "notAList");
        expectIOException(ext, SubjectInfoAccessExtension.DESCRIPTIONS, null);
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
