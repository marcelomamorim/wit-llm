/*
 * @test
 * @summary Witup generated check for SubjectAlternativeNameExtension.encode(OutputStream) on a default instance
 * @modules java.base/sun.security.x509
 * @run main SubjectAlternativeNameExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.x509.SubjectAlternativeNameExtension;

public class SubjectAlternativeNameExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ext.encode(bos);
        } catch (IOException e) {
            return;
        }
        byte[] out = bos.toByteArray();
        if (out.length == 0 || out[0] != 0x30) {
            throw new RuntimeException("Encoded extension must be a DER SEQUENCE");
        }
    }
}
