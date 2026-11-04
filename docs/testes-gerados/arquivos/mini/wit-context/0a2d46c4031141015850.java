/*
 * @test
 * @summary Test sun.security.x509.SerialNumber constructor with DerInputStream
 * @run main SerialNumberWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import sun.security.util.DerInputStream;
import sun.security.x509.SerialNumber;

public class SerialNumberWitupTest {
    public static void main(String[] args) throws Exception {
        testValidDerInputStream();
        testInvalidDerInputStreamEmpty();
        testInvalidDerInputStreamCorrupt();
    }

    private static void testValidDerInputStream() throws IOException {
        // DER encoding of INTEGER 1: 0x02 0x01 0x01
        byte[] validDer = new byte[] {0x02, 0x01, 0x01};
        DerInputStream derIn = new DerInputStream(validDer);
        SerialNumber sn = new SerialNumber(derIn);
        if (!sn.getNumber().equals(BigInteger.ONE)) {
            throw new RuntimeException("Expected serial number 1, got " + sn.getNumber());
        }
        String str = sn.toString();
        if (str == null || !str.contains("01")) {
            throw new RuntimeException("toString() does not contain expected hex representation");
        }
    }

    private static void testInvalidDerInputStreamEmpty() {
        // Empty input stream should cause IOException
        byte[] empty = new byte[0];
        DerInputStream derIn = new DerInputStream(empty);
        try {
            new SerialNumber(derIn);
            throw new RuntimeException("Expected IOException for empty input");
        } catch (IOException e) {
            // Expected
        }
    }

    private static void testInvalidDerInputStreamCorrupt() {
        // Corrupt DER encoding: tag 0x02 but length 0x02 and only 1 byte data
        byte[] corruptDer = new byte[] {0x02, 0x02, 0x01};
        DerInputStream derIn = new DerInputStream(corruptDer);
        try {
            new SerialNumber(derIn);
            throw new RuntimeException("Expected IOException for corrupt DER input");
        } catch (IOException e) {
            // Expected
        }
    }
}
