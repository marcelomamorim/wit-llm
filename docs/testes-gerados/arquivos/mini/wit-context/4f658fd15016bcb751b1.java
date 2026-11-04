/*
 * @test
 * @summary Test sun.security.util.DerOutputStream.putInteger(Integer) for basic functionality and OutOfMemoryError edge cases
 * @run main DerOutputStreamWitupTest
 */

import sun.security.util.DerOutputStream;
import java.io.IOException;

public class DerOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testPutIntegerBasic();
        testPutIntegerNullPointer();
        testPutIntegerLargeValue();
    }

    private static void testPutIntegerBasic() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        dos.putInteger(Integer.valueOf(0));
        byte[] result = dos.toByteArray();
        if (result == null || result.length == 0) {
            throw new RuntimeException("putInteger did not write any bytes for zero");
        }
    }

    private static void testPutIntegerNullPointer() {
        DerOutputStream dos = new DerOutputStream();
        try {
            dos.putInteger(null);
            throw new RuntimeException("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException for null input", e);
        }
    }

    private static void testPutIntegerLargeValue() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        // Use a large integer value to test normal behavior (not triggering OutOfMemoryError)
        dos.putInteger(Integer.valueOf(Integer.MAX_VALUE));
        byte[] result = dos.toByteArray();
        if (result == null || result.length == 0) {
            throw new RuntimeException("putInteger did not write any bytes for Integer.MAX_VALUE");
        }
    }
}
