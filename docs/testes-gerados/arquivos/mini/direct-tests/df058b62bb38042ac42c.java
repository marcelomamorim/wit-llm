/*
 * @test
 * @summary Test BufferedInputStream.read() method behavior
 * @run main BufferedInputStreamWitupTest
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BufferedInputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testReadReturnsBytesCorrectly();
        testReadReturnsMinusOneAtEndOfStream();
    }

    private static void testReadReturnsBytesCorrectly() throws IOException {
        byte[] data = {10, 20, 30, 40, 50};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        BufferedInputStream bis = new BufferedInputStream(bais, 3);

        for (int i = 0; i < data.length; i++) {
            int readByte = bis.read();
            if (readByte != (data[i] & 0xff)) {
                throw new AssertionError("Expected byte " + (data[i] & 0xff) + " but got " + readByte);
            }
        }

        bis.close();
    }

    private static void testReadReturnsMinusOneAtEndOfStream() throws IOException {
        byte[] data = {};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        BufferedInputStream bis = new BufferedInputStream(bais);

        int readByte = bis.read();
        if (readByte != -1) {
            throw new AssertionError("Expected -1 at end of stream but got " + readByte);
        }

        bis.close();
    }
}
