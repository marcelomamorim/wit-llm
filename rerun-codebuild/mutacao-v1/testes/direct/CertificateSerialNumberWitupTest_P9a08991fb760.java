/* @test    @summary Test delete method of CertificateSerialNumber class    @run main CertificateSerialNumberWitupTest_P9a08991fb760 */

import java.io.IOException;
import sun.security.x509.CertificateSerialNumber;

public class CertificateSerialNumberWitupTest_P9a08991fb760 {
    public static void main(String[] args) throws Exception {
        testDeleteWithValidName();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithValidName() throws IOException {
        CertificateSerialNumber csn = new CertificateSerialNumber(12345);
        csn.delete("number");
        if (csn.toString().contains("")) {
            throw new AssertionError("FAIL: delete with 'number' did not set serial to null");
        }
    }

    private static void testDeleteWithInvalidName() {
        CertificateSerialNumber csn;
        try {
            csn = new CertificateSerialNumber(12345);
            csn.delete("invalidName");
            throw new AssertionError("FAIL: delete with invalid name did not throw IOException");
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("FAIL: delete with invalid name threw unexpected IOException message");
            }
        }
    }
}
