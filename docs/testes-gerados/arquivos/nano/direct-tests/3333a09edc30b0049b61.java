/* @test    @summary Test deserialization constructor of CertificateSerialNumber using InputStream  @run main CertificateSerialNumberWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import sun.security.x509.CertificateSerialNumber;
import sun.security.util.SerialNumber;

public class CertificateSerialNumberWitupTest {

    public static void main(String[] args) throws IOException {
        testConstructorWithValidInput();
        testConstructorWithEmptyInput();
    }

    static void testConstructorWithValidInput() throws IOException {
        byte[] serialBytes = {0x02, 0x01, 0x0A}; // DER encoding for integer 10
        InputStream in = new ByteArrayInputStream(serialBytes);
        CertificateSerialNumber csn = new CertificateSerialNumber(in);
        assert csn != null : "CertificateSerialNumber should not be null";
        String str = csn.toString();
        assert str.contains("10") : "Serial number string should contain '10'";
    }

    static void testConstructorWithEmptyInput() {
        byte[] emptyBytes = {};
        InputStream in = new ByteArrayInputStream(emptyBytes);
        try {
            new CertificateSerialNumber(in);
            assert false : "Expected IOException for empty input";
        } catch (IOException e) {
            assert e != null : "IOException should be thrown";
        }
    }
}
