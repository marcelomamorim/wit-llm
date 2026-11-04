/* @test    @summary Test DerOutputStream.putNull() for expected behavior and exception handling    @run main DerOutputStreamWitupTest */

import sun.security.util.DerOutputStream;
import java.io.IOException;

public class DerOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testPutNullNormal();
        testPutNullMemoryError();
    }

    static void testPutNullNormal() {
        DerOutputStream dos = new DerOutputStream();
        try {
            dos.putNull();
            byte[] result = dos.toByteArray();
            // Expecting tag for NULL (0x05) and length 0
            if (result.length == 2 && result[0] == 0x05 && result[1] == 0x00) {
                System.out.println("testPutNullNormal passed");
            } else {
                System.err.println("testPutNullNormal failed: unexpected output: " + bytesToHex(result));
            }
        } catch (IOException e) {
            System.err.println("testPutNullNormal failed with IOException: " + e);
        }
    }

    static void testPutNullMemoryError() {
        DerOutputStream dos = new DerOutputStream() {
            @Override
            public void write(byte[] buf, int off, int len) throws IOException {
                throw new OutOfMemoryError("Simulated OutOfMemoryError");
            }
        };
        try {
            dos.putNull();
            System.err.println("testPutNullMemoryError failed: no exception thrown");
        } catch (OutOfMemoryError e) {
            System.out.println("testPutNullMemoryError passed");
        } catch (Throwable t) {
            System.err.println("testPutNullMemoryError failed with unexpected exception: " + t);
        }
    }

    static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
