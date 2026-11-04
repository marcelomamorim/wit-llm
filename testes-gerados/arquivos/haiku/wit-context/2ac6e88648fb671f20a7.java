/*
 * @test
 * @summary Witup-generated checks for SubjectAlternativeNameExtension.set exception paths
 * @modules java.base/sun.security.x509
 * @run main SubjectAlternativeNameExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.SubjectAlternativeNameExtension;

public class SubjectAlternativeNameExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension();

        expectIOException(ext, "unknownName", null, "not recognized");
        expectIOException(ext, "unknownName", "value", "not recognized");
        expectIOException(ext, "subject_name", "notGeneralNames", "type GeneralNames");
        expectIOException(ext, "SUBJECT_NAME", Integer.valueOf(1), "type GeneralNames");
        expectIOException(ext, "Subject_Name", "value", "type GeneralNames");
        expectIOException(ext, "subject_name", null, "type GeneralNames");
    }

    static void expectIOException(SubjectAlternativeNameExtension ext, String name,
                                  Object obj, String expectedMessagePart) {
        try {
            ext.set(name, obj);
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains(expectedMessagePart)) {
                throw new RuntimeException("Unexpected message for name=" + name
                        + ": " + msg);
            }
            return;
        }
        throw new RuntimeException("Expected IOException for name=" + name);
    }
}
