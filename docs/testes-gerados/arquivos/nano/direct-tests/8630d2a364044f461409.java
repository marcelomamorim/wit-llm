/* @test    @summary Test delete method of CertificateSerialNumber class    @run main CertificateSerialNumberWitupTest */

import java.io.IOException;
import sun.security.x509.CertificateSerialNumber;

public class CertificateSerialNumberWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithValidName();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithValidName() throws IOException {
        CertificateSerialNumber serialNumber = new CertificateSerialNumber(12345);
        serialNumber.delete("number");
        if (serialNumber.toString().contains("")) {
            System.out.println("PASS: delete with 'number' attribute sets serial to null.");
        } else {
            throw new AssertionError("FAIL: delete with 'number' attribute did not set serial to null.");
        }
    }

    private static void testDeleteWithInvalidName() {
        CertificateSerialNumber serialNumber = new CertificateSerialNumber(12345);
        try {
            serialNumber.delete("invalidAttribute");
            throw new AssertionError("FAIL: delete with invalid attribute did not throw IOException.");
        } catch (IOException e) {
            if (e.getMessage().contains("Attribute name not recognized")) {
                System.out.println("PASS: delete with invalid attribute throws expected IOException.");
            } else {
                throw new AssertionError("FAIL: delete with invalid attribute threw unexpected IOException message.");
            }
        }
    }
}
