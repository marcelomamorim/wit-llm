/*
 * @test
 * @summary Test CertificateSerialNumber constructor with DerValue input
 * @run main CertificateSerialNumberWitupTest
 */

import java.io.IOException;
import sun.security.x509.CertificateSerialNumber;
sun.security.util.DerValue;

public class CertificateSerialNumberWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a valid DerValue representing an integer serial number
        // Using DerValue constructor with integer tag and byte array
        byte[] serialBytes = {0x02, 0x01, 0x01}; // DER encoding for INTEGER 1
        DerValue val = new DerValue(serialBytes);

        CertificateSerialNumber csn = new CertificateSerialNumber(val);
        String str = csn.toString();

        if (!"1".equals(str)) {
            throw new RuntimeException("Expected serial number string '1' but got: " + str);
        }

        // Test toString returns empty string if serial is null (simulate by reflection not possible, so skip)
        // Test that constructor throws IOException on invalid DerValue
        try {
            // Create invalid DerValue (empty or wrong tag)
            DerValue invalidVal = new DerValue(new byte[] {0x05, 0x00}); // NULL tag
            new CertificateSerialNumber(invalidVal);
            throw new RuntimeException("Expected IOException not thrown for invalid DerValue");
        } catch (IOException expected) {
            // Expected exception
        }
    }
}
