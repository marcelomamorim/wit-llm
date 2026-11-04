/*
 * @test
 * @summary Test sun.security.util.DerOutputStream.putBoolean(boolean) for correct DER encoding and basic behavior
 * @run main DerOutputStreamWitupTest
 */

import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import java.io.IOException;
import java.util.Arrays;

public class DerOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testPutBooleanTrue();
        testPutBooleanFalse();
    }

    private static void testPutBooleanTrue() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        dos.putBoolean(true);
        byte[] encoded = dos.toByteArray();
        // Expected DER encoding for BOOLEAN TRUE: tag(0x01), length(0x01), value(0xFF)
        byte[] expected = new byte[] { DerValue.tag_Boolean, 0x01, (byte)0xFF };
        if (!Arrays.equals(encoded, expected)) {
            throw new AssertionError("putBoolean(true) produced incorrect encoding: " + Arrays.toString(encoded));
        }
    }

    private static void testPutBooleanFalse() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        dos.putBoolean(false);
        byte[] encoded = dos.toByteArray();
        // Expected DER encoding for BOOLEAN FALSE: tag(0x01), length(0x01), value(0x00)
        byte[] expected = new byte[] { DerValue.tag_Boolean, 0x01, 0x00 };
        if (!Arrays.equals(encoded, expected)) {
            throw new AssertionError("putBoolean(false) produced incorrect encoding: " + Arrays.toString(encoded));
        }
    }
}
