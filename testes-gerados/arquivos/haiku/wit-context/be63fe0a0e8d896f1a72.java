/*
 * @test
 * @summary Behavioral tests for ChunkedInputStream.read() on chunked bodies
 * @modules java.base/sun.net.www.http
 * @run main ChunkedInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import sun.net.www.http.ChunkedInputStream;

public class ChunkedInputStreamWitupTest {

    private static ChunkedInputStream newStream(String raw) {
        InputStream in = new ByteArrayInputStream(raw.getBytes(StandardCharsets.ISO_8859_1));
        return new ChunkedInputStream(in, null, null);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void testReadsFirstByteOfChunk() throws Exception {
        ChunkedInputStream cis = newStream("4\r\nabcd\r\n");
        int b = cis.read();
        check(b == 'a', "expected 'a' (97) but was " + b);
    }

    private static void testReadsSequentialBytesWithinChunk() throws Exception {
        ChunkedInputStream cis = newStream("4\r\nabcd\r\n");
        check(cis.read() == 'a', "byte 1 mismatch");
        check(cis.read() == 'b', "byte 2 mismatch");
        check(cis.read() == 'c', "byte 3 mismatch");
        check(cis.read() == 'd', "byte 4 mismatch");
    }

    private static void testReturnsUnsignedValue() throws Exception {
        ChunkedInputStream cis = newStream("1\r\n\u00ff\r\n");
        int b = cis.read();
        check(b == 255, "expected unsigned 255 but was " + b);
    }

    public static void main(String[] args) throws Exception {
        testReadsFirstByteOfChunk();
        testReadsSequentialBytesWithinChunk();
        testReturnsUnsignedValue();
    }
}
