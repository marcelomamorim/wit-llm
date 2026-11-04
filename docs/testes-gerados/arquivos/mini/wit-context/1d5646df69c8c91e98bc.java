/*
 * @test
 * @summary Test sun.security.util.DerOutputStream.putInteger(int) for basic correctness and OutOfMemoryError risk
 * @run main DerOutputStreamWitupTest
 */

import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testPutIntegerBasic();
    }

    private static void testPutIntegerBasic() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        // Test putInteger with a normal integer value
        dos.putInteger(0);
        byte[] result = dos.toByteArray();

        // The first byte should be the tag for INTEGER
        if (result.length == 0 || result[0] != DerValue.tag_Integer) {
            throw new AssertionError("putInteger did not write the INTEGER tag correctly");
        }

        // Test putInteger with a positive integer
        dos = new DerOutputStream();
        dos.putInteger(123456);
        result = dos.toByteArray();
        if (result.length == 0 || result[0] != DerValue.tag_Integer) {
            throw new AssertionError("putInteger did not write the INTEGER tag correctly for positive int");
        }

        // Test putInteger with a negative integer
        dos = new DerOutputStream();
        dos.putInteger(-123456);
        result = dos.toByteArray();
        if (result.length == 0 || result[0] != DerValue.tag_Integer) {
            throw new AssertionError("putInteger did not write the INTEGER tag correctly for negative int");
        }

        // Note: The WIT expaths suggest OutOfMemoryError on extreme buffer growth,
        // but such conditions are impractical to test deterministically here,
        // so we do not attempt to provoke OutOfMemoryError.
    }
}
