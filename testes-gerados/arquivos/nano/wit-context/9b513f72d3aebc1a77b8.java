/* @test    @summary Test for sun.security.x509.CertificateSerialNumber.get(String) method
 * @run main CertificateSerialNumberWitupTest
 */

import java.io.IOException;
import sun.security.x509.CertificateSerialNumber;
import sun.security.util.SerialNumber;

public class CertificateSerialNumberWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithNumber();
        testGetWithInvalidName();
    }

    private static void testGetWithNumber() throws IOException {
        SerialNumber sn = new SerialNumber(12345);
        CertificateSerialNumber certSN = new CertificateSerialNumber(sn);
        SerialNumber result = certSN.get("number");
        if (result == null || !result.equals(sn)) {
            throw new AssertionError("Expected serial number to match input.");
        }
    }

    private static void testGetWithInvalidName() {
        try {
            CertificateSerialNumber certSN = new CertificateSerialNumber(BigInteger.valueOf(67890));
            certSN.get("invalidName");
            throw new AssertionError("Expected IOException for invalid attribute name.");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }
}
