/* @test
   @summary Tests the delete method of CertificateSerialNumber.
   @run main CertificateSerialNumberWitupTest_Pd42f2f8974ef
 */
import java.io.IOException;
import sun.security.x509.CertificateSerialNumber;
public class CertificateSerialNumberWitupTest_Pd42f2f8974ef {
    public static void main(String[] args) throws Exception {
        testDeleteWithValidName();
        testDeleteWithInvalidName();
    }
    private static void testDeleteWithValidName() throws Exception {
        CertificateSerialNumber serialNumber = new CertificateSerialNumber(12345);
        serialNumber.delete("number");
        if (serialNumber.toString().contains("")) {
            throw new AssertionError("Expected serial to be null or empty after delete.");
        }
    }
    private static void testDeleteWithInvalidName() {
        CertificateSerialNumber serialNumber;
        try {
            serialNumber = new CertificateSerialNumber(12345);
            serialNumber.delete("invalidName");
            throw new AssertionError("Expected IOException for invalid attribute name.");
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}
