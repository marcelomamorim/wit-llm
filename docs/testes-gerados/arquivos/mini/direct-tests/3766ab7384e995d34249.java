/*
 * @test
 * @summary Test sun.security.util.DerOutputStream.putEnumerated(int) method
 * @run main sun.security.util.DerOutputStreamWitupTest
 */

import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import java.io.IOException;
import java.util.Arrays;

public class DerOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testPutEnumeratedZero();
        testPutEnumeratedPositive();
        testPutEnumeratedNegative();
    }

    private static void testPutEnumeratedZero() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        dos.putEnumerated(0);
        byte[] encoded = dos.toByteArray();

        // Expected: tag_Enumerated (0x0A), length 1, value 0
        byte[] expected = new byte[] { DerValue.tag_Enumerated, 0x01, 0x00 };
        if (!Arrays.equals(encoded, expected)) {
            throw new AssertionError("putEnumerated(0) produced incorrect encoding: " + Arrays.toString(encoded));
        }
    }

    private static void testPutEnumeratedPositive() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        dos.putEnumerated(127); // max positive single byte value
        byte[] encoded = dos.toByteArray();

        // Expected: tag_Enumerated (0x0A), length 1, value 0x7F
        byte[] expected = new byte[] { DerValue.tag_Enumerated, 0x01, 0x7F };
        if (!Arrays.equals(encoded, expected)) {
            throw new AssertionError("putEnumerated(127) produced incorrect encoding: " + Arrays.toString(encoded));
        }
    }

    private static void testPutEnumeratedNegative() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        dos.putEnumerated(-1); // negative value
        byte[] encoded = dos.toByteArray();

        // Expected: tag_Enumerated (0x0A), length 1, value 0xFF (two's complement -1)
        byte[] expected = new byte[] { DerValue.tag_Enumerated, 0x01, (byte)0xFF };
        if (!Arrays.equals(encoded, expected)) {
            throw new AssertionError("putEnumerated(-1) produced incorrect encoding: " + Arrays.toString(encoded));
        }
    }
}
