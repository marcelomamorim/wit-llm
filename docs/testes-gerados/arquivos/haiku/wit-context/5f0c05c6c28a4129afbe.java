/*
 * @test
 * @summary Round-trip DER encoding of CertificateSubjectName via encode(OutputStream) and null output handling
 * @modules java.base/sun.security.x509
 * @run main CertificateSubjectNameWitupTest
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import sun.security.x509.CertificateSubjectName;

public class CertificateSubjectNameWitupTest {
    public static void main(String[] args) throws Exception {
        byte[] empty = {0x30, 0x00};
        CertificateSubjectName csn = new CertificateSubjectName(new ByteArrayInputStream(empty));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        csn.encode(out);
        check(Arrays.equals(empty, out.toByteArray()), "round trip of empty name failed");
        ByteArrayOutputStream out2 = new ByteArrayOutputStream();
        csn.encode(out2);
        check(Arrays.equals(out.toByteArray(), out2.toByteArray()), "repeated encode differs");
        boolean npe = false;
        try {
            csn.encode(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "encode with null stream must throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
