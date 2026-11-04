/* @test    @summary Test DerOutputStream.putEnumerated for expected behavior and exception handling    @run main DerOutputStreamWitupTest */

import sun.security.util.DerOutputStream;
import java.io.IOException;

public class DerOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testPutEnumeratedNormal();
        testPutEnumeratedOutOfMemoryError();
    }

    static void testPutEnumeratedNormal() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        int value = 5;
        dos.putEnumerated(value);
        byte[] result = dos.toByteArray();
        // Expect the first byte to be the tag for Enumerated (0x0A)
        assert result[0] == (byte)0x0A : "Expected tag 0x0A, got: " + result[0];
        // The second byte should be the integer value
        assert result[1] == (byte)value : "Expected value byte: " + value + ", got: " + result[1];
    }

    static void testPutEnumeratedOutOfMemoryError() {
        DerOutputStream dos = new DerOutputStream() {
            @Override
            public void write(byte[] buf, int off, int len) throws IOException {
                throw new OutOfMemoryError("Simulated OOM");
            }
        };
        try {
            dos.putEnumerated(1);
            assert false : "Expected OutOfMemoryError to be thrown";
        } catch (OutOfMemoryError e) {
            assert e.getMessage().equals("Simulated OOM") : "Unexpected error message: " + e.getMessage();
        }
    }
}
