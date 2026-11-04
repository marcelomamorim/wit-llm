/* @test    @summary Test for CertificateVersion(InputStream in) constructor    @run main CertificateVersionWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import sun.security.x509.CertificateVersion;

public class CertificateVersionWitupTest {

    public static void main(String[] args) throws IOException {
        testValidVersion();
        testInvalidVersion();
        testConstructFromDerValue();
    }

    private static void testValidVersion() throws IOException {
        byte[] derDataV1 = {0x02, 0x01, 0x00}; // INTEGER 0
        InputStream in = new ByteArrayInputStream(derDataV1);
        CertificateVersion cv = new CertificateVersion(in);
        assert cv != null : "CertificateVersion should not be null";
        assert cv.getVersion() == 0 : "Expected version 0";
    }

    private static void testInvalidVersion() {
        byte[] derDataInvalid = {0x02, 0x01, 0x03}; // INTEGER 3 (invalid)
        InputStream in = new ByteArrayInputStream(derDataInvalid);
        try {
            new CertificateVersion(in);
            assert false : "Expected IOException for invalid version";
        } catch (IOException e) {
            assert e.getMessage().contains("bad format") || e.getMessage().contains("not supported") : "Unexpected exception message: " + e.getMessage();
        }
    }

    private static void testConstructFromDerValue() throws IOException {
        byte[] derDataV2 = {0x02, 0x01, 0x01}; // INTEGER 1
        DerValue derValue = new DerValue(derDataV2);
        InputStream in = new ByteArrayInputStream(derValue.toByteArray());
        CertificateVersion cv = new CertificateVersion(in);
        assert cv != null : "CertificateVersion should not be null";
        assert cv.getVersion() == 1 : "Expected version 1";
    }
}
