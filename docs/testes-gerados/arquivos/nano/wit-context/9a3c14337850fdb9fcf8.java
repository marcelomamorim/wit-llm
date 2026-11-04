/* @test    @summary Test DerOutputStream.putBoolean for normal and edge cases    @run main DerOutputStreamWitupTest */

import sun.security.util.DerOutputStream;
import java.io.IOException;

public class DerOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testPutBooleanTrue();
        testPutBooleanFalse();
        testPutBooleanMemoryError();
    }

    static void testPutBooleanTrue() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        dos.putBoolean(true);
        byte[] result = dos.toByteArray();
        // Expected: tag (1 byte), length (1 byte), value (1 byte)
        assert result.length == 3 : "Result length mismatch";
        assert result[0] == DerOutputStream.tag_Boolean : "Incorrect tag";
        assert result[1] == 1 : "Incorrect length";
        assert result[2] == (byte) 0xff : "Incorrect value for true";
    }

    static void testPutBooleanFalse() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        dos.putBoolean(false);
        byte[] result = dos.toByteArray();
        assert result.length == 3 : "Result length mismatch";
        assert result[0] == DerOutputStream.tag_Boolean : "Incorrect tag";
        assert result[1] == 1 : "Incorrect length";
        assert result[2] == 0 : "Incorrect value for false";
    }

    static void testPutBooleanMemoryError() {
        DerOutputStream dos = new DerOutputStream() {
            @Override
            public void write(byte b) throws IOException {
                throw new OutOfMemoryError("Simulated OOM") ;
            }
        };
        try {
            dos.putBoolean(true);
            assert false : "Expected OutOfMemoryError";
        } catch (OutOfMemoryError e) {
            // Expected
        }
    }
}
