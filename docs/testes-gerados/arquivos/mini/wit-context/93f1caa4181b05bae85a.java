/*
 * @test
 * @summary Test java.util.Base64.DecInputStream.read() method for IOException on closed stream and normal read
 * @run main Base64WitupTest
 */

import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.DecInputStream;

public class Base64WitupTest {

    public static void main(String[] args) throws Exception {
        testReadReturnsByte();
        testReadReturnsMinusOneOnEmpty();
        testReadThrowsIOExceptionWhenClosed();
    }

    private static void testReadReturnsByte() throws IOException {
        Decoder decoder = Base64.getDecoder();
        byte[] encoded = "SGVsbG8=".getBytes("US-ASCII"); // "Hello" in Base64
        DecInputStream dis = new DecInputStream(new java.io.ByteArrayInputStream(encoded), decoder);
        int b = dis.read();
        if (b == -1) {
            throw new RuntimeException("read() returned -1 unexpectedly on valid input");
        }
        if (b != 'H') {
            throw new RuntimeException("read() returned wrong byte: " + b);
        }
        dis.close();
    }

    private static void testReadReturnsMinusOneOnEmpty() throws IOException {
        Decoder decoder = Base64.getDecoder();
        byte[] empty = new byte[0];
        DecInputStream dis = new DecInputStream(new java.io.ByteArrayInputStream(empty), decoder);
        int b = dis.read();
        if (b != -1) {
            throw new RuntimeException("read() should return -1 on empty input but returned " + b);
        }
        dis.close();
    }

    private static void testReadThrowsIOExceptionWhenClosed() {
        Decoder decoder = Base64.getDecoder();
        byte[] encoded = "SGVsbG8=".getBytes();
        DecInputStream dis = new DecInputStream(new java.io.ByteArrayInputStream(encoded), decoder);
        try {
            dis.close();
            dis.read();
            throw new RuntimeException("read() did not throw IOException after stream was closed");
        } catch (IOException e) {
            // Expected IOException on read after close
        }
    }
}
