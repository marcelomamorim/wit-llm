/* @test    @summary Test the get method of CertificateSerialNumber for valid and invalid attribute names    @run main CertificateSerialNumberWitupTest */

import java.io.IOException;
import java.math.BigInteger;
import sun.security.x509.CertificateSerialNumber;
import sun.security.util.SerialNumber;

public class CertificateSerialNumberWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws IOException {
        BigInteger bigInt = new BigInteger("123456789");
        CertificateSerialNumber certSerial = new CertificateSerialNumber(bigInt);
        SerialNumber serial = certSerial.get("number");
        assert serial != null : "SerialNumber should not be null for valid name";
        assert serial.toString().equals(bigInt.toString()) : "SerialNumber value mismatch";
    }

    private static void testGetWithInvalidName() {
        BigInteger bigInt = new BigInteger("987654321");
        CertificateSerialNumber certSerial;
        try {
            certSerial = new CertificateSerialNumber(bigInt);
            certSerial.get("invalidName");
            assert false : "Expected IOException for invalid attribute name";
        } catch (IOException e) {
            assert e.getMessage().contains("Attribute name not recognized") : "Unexpected exception message";
        }
    }
}
