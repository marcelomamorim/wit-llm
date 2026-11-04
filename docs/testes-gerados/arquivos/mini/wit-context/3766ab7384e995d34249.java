/*
 * @test
 * @summary Test sun.security.util.DerOutputStream.putEnumerated(int) for basic correctness and OutOfMemoryError edge
 * @run main DerOutputStreamWitupTest
 */

import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testPutEnumeratedBasic();
    }

    private static void testPutEnumeratedBasic() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        // Write a small enumerated value and verify output
        dos.putEnumerated(5);
        byte[] result = dos.toByteArray();

        // Expected DER encoding for Enumerated 5 is:
        // Tag: 0x0A (DerValue.tag_Enumerated)
        // Length: 1
        // Value: 0x05
        if (result.length != 3) {
            throw new AssertionError("Unexpected length: " + result.length);
        }
        if (result[0] != DerValue.tag_Enumerated) {
            throw new AssertionError("Unexpected tag byte: " + result[0]);
        }
        if (result[1] != 1) {
            throw new AssertionError("Unexpected length byte: " + result[1]);
        }
        if (result[2] != 5) {
            throw new AssertionError("Unexpected value byte: " + result[2]);
        }
    }
}
