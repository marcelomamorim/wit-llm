/* @test    @summary Test constructor SerialNumber(DerInputStream in) for SerialNumber class    @run main SerialNumberWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import sun.security.x509.SerialNumber;

public class SerialNumberWitupTest {

    public static void main(String[] args) throws IOException {
        testValidDerInput();
        testInvalidDerInput();
    }

    private static void testValidDerInput() throws IOException {
        BigInteger bigInt = BigInteger.valueOf(12345);
        DerValue derValue = new DerValue(bigInt.toByteArray());
        byte[] derBytes = derValue.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(derBytes);
        DerInputStream derInputStream = new DerInputStream(bais);
        SerialNumber sn = new SerialNumber(derInputStream);
        assert sn.getNumber().equals(bigInt) : "SerialNumber does not match input";
        System.out.println("testValidDerInput passed");
    }

    private static void testInvalidDerInput() {
        byte[] invalidBytes = {0x00, 0x01, 0x02};
        ByteArrayInputStream bais = new ByteArrayInputStream(invalidBytes);
        try {
            DerInputStream derInputStream = new DerInputStream(bais);
            new SerialNumber(derInputStream);
            assert false : "Expected IOException was not thrown";
        } catch (IOException e) {
            System.out.println("testInvalidDerInput passed with expected exception: " + e.getMessage());
        }
    }
}
