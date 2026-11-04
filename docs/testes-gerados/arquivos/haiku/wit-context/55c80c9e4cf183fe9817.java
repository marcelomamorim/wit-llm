/*
 * @test
 * @summary Verifies CertificateIssuerName.encode(OutputStream) writes the DER form and propagates IOException from the target stream
 * @modules java.base/sun.security.x509
 * @run main CertificateIssuerNameWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import sun.security.x509.CertificateIssuerName;

public class CertificateIssuerNameWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] der = {0x30, 0x00};
        CertificateIssuerName cin =
            new CertificateIssuerName(new ByteArrayInputStream(der));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        cin.encode(out);
        if (!Arrays.equals(der, out.toByteArray())) {
            throw new RuntimeException("encode output differs from DER input");
        }

        OutputStream failing = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                throw new IOException("boom");
            }
        };
        try {
            cin.encode(failing);
            throw new RuntimeException("expected IOException was not thrown");
        } catch (IOException expected) {
            // expected
        }
    }
}
