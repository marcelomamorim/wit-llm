/*
 * @test
 * @summary Test sun.security.util.DerOutputStream.putInteger(int) method
 * @run main DerOutputStreamWitupTest
 */

import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import java.io.IOException;
import java.util.Arrays;

public class DerOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testPutIntegerZero();
        testPutIntegerPositive();
        testPutIntegerNegative();
    }

    private static void testPutIntegerZero() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        dos.putInteger(0);
        byte[] encoded = dos.toByteArray();
        // Expected DER encoding for integer 0: tag(0x02), length(0x01), value(0x00)
        byte[] expected = new byte[] {DerValue.tag_Integer, 0x01, 0x00};
        if (!Arrays.equals(encoded, expected)) {
            throw new RuntimeException("putInteger(0) produced incorrect encoding: " + Arrays.toString(encoded));
        }
    }

    private static void testPutIntegerPositive() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        dos.putInteger(127); // 0x7F
        byte[] encoded = dos.toByteArray();
        // Expected DER encoding: tag(0x02), length(0x01), value(0x7F)
        byte[] expected = new byte[] {DerValue.tag_Integer, 0x01, 0x7F};
        if (!Arrays.equals(encoded, expected)) {
            throw new RuntimeException("putInteger(127) produced incorrect encoding: " + Arrays.toString(encoded));
        }
    }

    private static void testPutIntegerNegative() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        dos.putInteger(-128); // 0xFFFFFF80 in 4 bytes, but DER minimal encoding expected
        byte[] encoded = dos.toByteArray();
        // Expected DER encoding for -128 is tag(0x02), length(0x01), value(0x80)
        byte[] expected = new byte[] {DerValue.tag_Integer, 0x01, (byte)0x80};
        if (!Arrays.equals(encoded, expected)) {
            throw new RuntimeException("putInteger(-128) produced incorrect encoding: " + Arrays.toString(encoded));
        }
    }
}
