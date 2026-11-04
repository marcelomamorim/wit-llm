/* @test    @summary Test SerialNumber(InputStream in) constructor for correct decoding and exception handling    @run main SerialNumberWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import sun.security.util.DerValue;
import sun.security.x509.SerialNumber;

public class SerialNumberWitupTest {

    public static void main(String[] args) {
        testValidInput();
        testInvalidInput();
    }

    private static void testValidInput() {
        try {
            BigInteger bigInt = BigInteger.valueOf(12345);
            DerValue derVal = new DerValue(bigInt.toByteArray());
            ByteArrayInputStream bais = new ByteArrayInputStream(derVal.getDerEncoded());
            SerialNumber sn = new SerialNumber(bais);
            assert sn.getNumber().equals(bigInt) : "SerialNumber does not match input";
            System.out.println("testValidInput passed");
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testValidInput", e);
        }
    }

    private static void testInvalidInput() {
        try {
            byte[] invalidData = {0x00, 0x01, 0x02};
            ByteArrayInputStream bais = new ByteArrayInputStream(invalidData);
            new SerialNumber(bais);
            throw new AssertionError("Expected IOException not thrown for invalid input");
        } catch (IOException e) {
            System.out.println("testInvalidInput passed with expected exception: " + e.getMessage());
        }
    }
}
