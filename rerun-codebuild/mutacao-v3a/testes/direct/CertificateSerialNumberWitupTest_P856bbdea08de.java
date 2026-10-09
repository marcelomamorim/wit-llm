/* @test    @summary Test delete method of CertificateSerialNumber class    @run main CertificateSerialNumberWitupTest_P856bbdea08de */

import java.io.IOException;
import sun.security.x509.CertificateSerialNumber;
import sun.security.util.SerialNumber;

public class CertificateSerialNumberWitupTest_P856bbdea08de {

    public static void main(String[] args) {
        testDeleteWithValidNumber();
        testDeleteWithInvalidName();
    }

    public static void testDeleteWithValidNumber() {
        try {
            SerialNumber sn = new SerialNumber(12345);
            CertificateSerialNumber certSerial = new CertificateSerialNumber(sn);
            certSerial.delete("number");
            if (certSerial.serial != null) {
                throw new AssertionError("serial should be null after delete with 'number'");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e.getMessage());
        }
    }

    public static void testDeleteWithInvalidName() {
        try {
            SerialNumber sn = new SerialNumber(54321);
            CertificateSerialNumber certSerial = new CertificateSerialNumber(sn);
            certSerial.delete("invalidName");
            throw new AssertionError("Expected IOException was not thrown for invalid name");
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
