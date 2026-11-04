/*
 * @test
 * @summary Test CertificateSerialNumber.get(String) method behavior
 * @run main CertificateSerialNumberWitupTest
 */

import java.io.IOException;
import java.math.BigInteger;
import sun.security.x509.CertificateSerialNumber;
import sun.security.x509.SerialNumber;

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
            throw new AssertionError("get(\"number\") returned null");
        }
        // Also test case-insensitivity
        SerialNumber sn2 = csn.get("NUMBER");
        if (sn2 == null) {
            throw new AssertionError("get(\"NUMBER\") returned null");
        }
        if (!sn.toString().equals(sn2.toString())) {
            throw new AssertionError("SerialNumber mismatch for case-insensitive keys");
        }
    }

    private static void testGetWithInvalidName() {
        try {
            BigInteger bigInt = BigInteger.valueOf(1L);
            CertificateSerialNumber csn = new CertificateSerialNumber(bigInt);
            csn.get("invalidName");
            throw new AssertionError("Expected IOException not thrown for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new AssertionError("IOException message unexpected: " + msg);
            }
        }
    }
}
