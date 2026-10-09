/*
 * @test
 * @summary Test sun.security.x509.CertificateSerialNumber.delete(String) method
 * @run main CertificateSerialNumberWitupTest_P51a738684d3e
 */

import java.io.IOException;
import java.math.BigInteger;
import sun.security.x509.CertificateSerialNumber;

public class CertificateSerialNumberWitupTest_P51a738684d3e {
    public static void main(String[] args) throws Exception {
        testDeleteWithValidName();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithValidName() throws IOException {
        CertificateSerialNumber csn = new CertificateSerialNumber(12345);
        csn.delete("number");
        // After delete with "number", toString should return empty string
        String s = csn.toString();
        if (s == null || s.length() != 0) {
            throw new AssertionError("Expected empty string after delete with 'number', got: " + s);
        }
    }

    private static void testDeleteWithInvalidName() {
        CertificateSerialNumber csn = new CertificateSerialNumber(12345);
        try {
            csn.delete("invalidName");
            throw new AssertionError("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new AssertionError("IOException message unexpected: " + msg);
            }
        }
    }
}
