/*
 * @test
 * @summary Verify that SubjectAlternativeNameExtension.encode(OutputStream) produces a stable DER SEQUENCE for the default extension
 * @modules java.base/sun.security.x509
 * @run main SubjectAlternativeNameExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.x509.SubjectAlternativeNameExtension;

public class SubjectAlternativeNameExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension();

        ByteArrayOutputStream out1 = new ByteArrayOutputStream();
        ext.encode(out1);
        byte[] first = out1.toByteArray();
        if (first.length == 0) {
            throw new RuntimeException("encode produced no bytes");
        }
        if (first[0] != 0x30) {
            throw new RuntimeException("expected DER SEQUENCE tag 0x30, got " + first[0]);
        }

        ByteArrayOutputStream out2 = new ByteArrayOutputStream();
        ext.encode(out2);
        byte[] second = out2.toByteArray();
        if (first.length != second.length) {
            throw new RuntimeException("repeated encode changed length");
        }
        for (int i = 0; i < first.length; i++) {
            if (first[i] != second[i]) {
                throw new RuntimeException("repeated encode changed byte at " + i);
            }
        }
    }
}
