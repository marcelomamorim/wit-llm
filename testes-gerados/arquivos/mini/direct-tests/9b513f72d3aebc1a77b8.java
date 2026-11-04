/*
 * @test
 * @summary Test CertificateSerialNumber.get(String) method behavior
 * @run main CertificateSerialNumberWitupTest
 */

import sun.security.x509.CertificateSerialNumber;
import sun.security.x509.SerialNumber;
import java.io.IOException;
import java.math.BigInteger;

public class CertificateSerialNumberWitupTest {
    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws IOException {
        BigInteger bigInt = BigInteger.valueOf(123456789L);
        CertificateSerialNumber csn = new CertificateSerialNumber(bigInt);
        SerialNumber sn = csn.get("number");
        if (sn == null) {
            throw new RuntimeException("get(\"number\") returned null");
        }
        // The toString of SerialNumber should contain the decimal representation
        String snStr = sn.toString();
        if (!snStr.contains("123456789")) {
            throw new RuntimeException("SerialNumber.toString() does not contain expected number");
        }
    }

    private static void testGetWithInvalidName() {
        try {
            CertificateSerialNumber csn = new CertificateSerialNumber(1);
            csn.get("invalidName");
            throw new RuntimeException("Expected IOException not thrown for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
