/* @test    @summary Test DerOutputStream.putInteger(int) for expected behavior and exception handling    @run main DerOutputStreamWitupTest */

import sun.security.util.DerOutputStream;
import java.io.IOException;

public class DerOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testPutIntegerNormal();
        testPutIntegerMemoryError();
    }

    static void testPutIntegerNormal() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        int value = 42;
        dos.putInteger(value);
        byte[] result = dos.toByteArray();
        boolean containsTag = false;
        for (byte b : result) {
            if (b == DerOutputStream.tag_Integer) {
                containsTag = true;
                break;
            }
        }
        if (!containsTag) {
            throw new AssertionError("Tag not found in output");
        }
        if (result.length == 0 || result[0] != DerOutputStream.tag_Integer) {
            throw new AssertionError("First byte should be the tag for Integer");
        }
    }

    static void testPutIntegerMemoryError() {
        DerOutputStream dos = new DerOutputStream() {
            @Override
            public void write(byte b) throws IOException {
                throw new OutOfMemoryError("Simulated OOM");
            }
        };
        try {
            dos.putInteger(1);
            throw new AssertionError("Expected OutOfMemoryError was not thrown");
        } catch (OutOfMemoryError e) {
            // Expected exception
        }
    }
}
