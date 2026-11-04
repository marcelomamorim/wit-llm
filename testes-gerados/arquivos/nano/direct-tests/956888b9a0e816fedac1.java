/* @test    @summary Test constructor SerialNumber(DerValue val) for sun.security.x509.SerialNumber    @run main SerialNumberWitupTest */

import java.io.IOException;
import java.math.BigInteger;
import sun.security.util.DerValue;
import sun.security.x509.SerialNumber;

public class SerialNumberWitupTest {

    public static void main(String[] args) throws IOException {
        testValidDerValue();
        testInvalidDerValue();
    }

    private static void testValidDerValue() throws IOException {
        BigInteger bigInt = BigInteger.valueOf(12345);
        DerValue derValue = new DerValue(bigInt.toByteArray());
        SerialNumber sn = new SerialNumber(derValue);
        assert sn.getNumber().equals(bigInt) : "SerialNumber does not match input";
        System.out.println("testValidDerValue passed");
    }

    private static void testInvalidDerValue() {
        byte[] invalidBytes = {0x02, 0x01, 0x00, 0x00}; // Extra data after integer
        DerValue derValue = new DerValue(invalidBytes);
        try {
            SerialNumber sn = new SerialNumber(derValue);
            assert false : "Expected IOException due to excess data";
        } catch (IOException e) {
            System.out.println("testInvalidDerValue passed with expected exception");
        }
    }
}
