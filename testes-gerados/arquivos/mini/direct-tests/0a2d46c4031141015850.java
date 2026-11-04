/*
 * @test
 * @summary Test SerialNumber constructor with DerInputStream
 * @run main SerialNumberWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import sun.security.util.DerInputStream;
import sun.security.x509.SerialNumber;

public class SerialNumberWitupTest {
    public static void main(String[] args) throws Exception {
        testValidSerialNumber();
        testExcessDataThrowsIOException();
    }

    private static void testValidSerialNumber() throws IOException {
        // DER encoding of INTEGER 12345
        byte[] derEncoded = new byte[] {0x02, 0x02, 0x30, 0x39};
        DerInputStream derIn = new DerInputStream(derEncoded);
        SerialNumber sn = new SerialNumber(derIn);
        BigInteger expected = BigInteger.valueOf(12345);
        if (!sn.getNumber().equals(expected)) {
            throw new RuntimeException("SerialNumber value mismatch: expected " + expected + " but got " + sn.getNumber());
        }
        String str = sn.toString();
        if (!str.contains("3039")) { // hex of 12345
            throw new RuntimeException("toString() does not contain expected hex representation: " + str);
        }
    }

    private static void testExcessDataThrowsIOException() {
        // DER encoding of INTEGER 1 with extra trailing byte (0x00)
        byte[] derEncoded = new byte[] {0x02, 0x01, 0x01, 0x00};
        DerInputStream derIn = new DerInputStream(derEncoded);
        try {
            new SerialNumber(derIn);
            throw new RuntimeException("Expected IOException due to excess data not thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Excess SerialNumber data")) {
                throw new RuntimeException("IOException thrown but message unexpected: " + msg);
            }
        }
    }
}
