/*
 * @test
 * @summary Test CertificateSerialNumber.delete(String) method behavior
 * @run main CertificateSerialNumberWitupTest
 */

import java.io.IOException;
import java.math.BigInteger;
import sun.security.x509.CertificateSerialNumber;

public class CertificateSerialNumberWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteWithValidName();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithValidName() throws Exception {
        CertificateSerialNumber csn = new CertificateSerialNumber(BigInteger.ONE);
        csn.delete("number"); // case-insensitive
        String str = csn.toString();
        if (!"".equals(str)) {
            throw new RuntimeException("Failed: delete did not nullify serial; toString()=" + str);
        }
    }

    private static void testDeleteWithInvalidName() {
        CertificateSerialNumber csn = new CertificateSerialNumber(1);
        try {
            csn.delete("invalidName");
            throw new RuntimeException("Failed: delete did not throw IOException for invalid name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Failed: IOException message unexpected: " + msg);
            }
        }
    }
}
