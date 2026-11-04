/*
 * @test
 * @summary Test sun.security.util.DerOutputStream.putNull() for correct DER null encoding and buffer growth
 * @run main DerOutputStreamWitupTest
 */

import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testPutNullBasic();
        testPutNullBufferGrowth();
    }

    private static void testPutNullBasic() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        dos.putNull();
        byte[] encoded = dos.toByteArray();
        // DER encoding for NULL is: tag 0x05, length 0x00
        if (encoded.length != 2) {
            throw new AssertionError("Encoded NULL length incorrect: " + encoded.length);
        }
        if (encoded[0] != DerValue.tag_Null) {
            throw new AssertionError("Encoded NULL tag incorrect: " + encoded[0]);
        }
        if (encoded[1] != 0) {
            throw new AssertionError("Encoded NULL length byte incorrect: " + encoded[1]);
        }
    }

    private static void testPutNullBufferGrowth() throws IOException {
        // Create DerOutputStream with small initial buffer to force growth
        DerOutputStream dos = new DerOutputStream(1);
        // Write a large number of NULLs to force buffer growth and check no exceptions
        final int count = 1000;
        for (int i = 0; i < count; i++) {
            dos.putNull();
        }
        // Check that the buffer size is at least count * 2 (each NULL encoded as 2 bytes)
        byte[] encoded = dos.toByteArray();
        if (encoded.length < count * 2) {
            throw new AssertionError("Buffer did not grow as expected: " + encoded.length);
        }
        // Spot check first and last NULL encoding
        if (encoded[0] != DerValue.tag_Null || encoded[1] != 0) {
            throw new AssertionError("First NULL encoding incorrect");
        }
        int lastIndex = encoded.length - 2;
        if (encoded[lastIndex] != DerValue.tag_Null || encoded[lastIndex + 1] != 0) {
            throw new AssertionError("Last NULL encoding incorrect");
        }
    }
}
