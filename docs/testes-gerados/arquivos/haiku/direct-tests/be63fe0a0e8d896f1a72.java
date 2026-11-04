/*
 * @test
 * @summary Verifies ChunkedInputStream.read() returns decoded chunk bytes in order and masks bytes to the 0-255 range
 * @modules java.base/sun.net.www.http
 * @run main ChunkedInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;

import sun.net.www.http.ChunkedInputStream;

public class ChunkedInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testReadAcrossChunks();
        testReadMasksHighBitByte();
    }

    static void testReadAcrossChunks() throws IOException {
        byte[] data = "5\r\nhello\r\n3\r\nabc\r\n0\r\n\r\n".getBytes("US-ASCII");
        ChunkedInputStream cis = new ChunkedInputStream(
                new ByteArrayInputStream(data), null, null);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int c = cis.read();
            if (c < 0) {
                throw new RuntimeException("Unexpected end of stream at index " + i);
            }
            sb.append((char) c);
        }
        if (!"helloabc".equals(sb.toString())) {
            throw new RuntimeException("Expected 'helloabc' but got '" + sb + "'");
        }
    }

    static void testReadMasksHighBitByte() throws IOException {
        byte[] data = new byte[] {'1', '\r', '\n', (byte) 0xFF, '\r', '\n', '0', '\r', '\n', '\r', '\n'};
        ChunkedInputStream cis = new ChunkedInputStream(
                new ByteArrayInputStream(data), null, null);
        int c = cis.read();
        if (c != 255) {
            throw new RuntimeException("Expected 255 but got " + c);
        }
    }
}
