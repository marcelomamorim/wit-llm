/*
 * @test
 * @summary Verify CertificateX509Key(InputStream) rejects an empty DER stream with IOException
 * @modules java.base/sun.security.x509
 * @run main CertificateX509KeyWitupTest
 */
import java.io.ByteArrayInputStream;
import java.io.IOException;
import sun.security.x509.CertificateX509Key;

public class CertificateX509KeyWitupTest {
    public static void main(String[] args) throws Exception {
        try {
            new CertificateX509Key(new ByteArrayInputStream(new byte[0]));
            throw new RuntimeException("IOException expected for empty input");
        } catch (IOException expected) {
        }
    }
}
