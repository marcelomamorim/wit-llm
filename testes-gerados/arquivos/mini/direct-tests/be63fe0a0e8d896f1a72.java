/*
 * @test
 * @summary Test sun.net.www.http.ChunkedInputStream.read() method behavior
 * @run main ChunkedInputStreamWitupTest
 */

import java.io.*;
import sun.net.www.http.*;

public class ChunkedInputStreamWitupTest {

    // Minimal stub classes to satisfy constructor dependencies
    static class DummyHttpClient extends HttpClient {
        public DummyHttpClient() {
            super(null, null, null);
        }
    }

    static class DummyMessageHeader extends MessageHeader {
        public DummyMessageHeader() {
            super();
        }
    }

    public static void main(String[] args) throws Exception {
        testReadReturnsMinusOneOnEmptyStream();
        testReadReturnsByteFromChunkData();
    }

    // Test that read() returns -1 when no data is available (readAhead returns <= 0)
    private static void testReadReturnsMinusOneOnEmptyStream() throws Exception {
        // Create an InputStream that returns EOF immediately
        InputStream emptyStream = new InputStream() {
            public int read() {
                return -1;
            }
        };

        DummyHttpClient hc = new DummyHttpClient();
        DummyMessageHeader mh = new DummyMessageHeader();

        ChunkedInputStream cis = new ChunkedInputStream(emptyStream, hc, mh);

        // Force chunkPos >= chunkCount to trigger readAhead
        // chunkPos and chunkCount are private, so we rely on initial state
        // The initial chunkCount is 0, chunkPos is 0, so chunkPos >= chunkCount is true

        int result = cis.read();
        if (result != -1) {
            throw new RuntimeException("Expected -1 on empty stream, got " + result);
        }
    }

    // Test that read() returns the correct byte from chunkData when available
    private static void testReadReturnsByteFromChunkData() throws Exception {
        // Create an InputStream that returns some data
        byte[] data = {0x41, 0x42, 0x43}; // 'A', 'B', 'C'
        InputStream dataStream = new ByteArrayInputStream(data);

        DummyHttpClient hc = new DummyHttpClient();
        DummyMessageHeader mh = new DummyMessageHeader();

        ChunkedInputStream cis = new ChunkedInputStream(dataStream, hc, mh);

        // We need to simulate that chunkData has data and chunkPos < chunkCount
        // Since chunkData, chunkPos, chunkCount are private, we cannot set them directly
        // Instead, we read bytes to fill chunkData via read()

        // The first read() will trigger readAhead and fill chunkData
        int first = cis.read();
        if (first != 0x41) {
            throw new RuntimeException("Expected first byte 0x41, got " + first);
        }

        int second = cis.read();
        if (second != 0x42) {
            throw new RuntimeException("Expected second byte 0x42, got " + second);
        }

        int third = cis.read();
        if (third != 0x43) {
            throw new RuntimeException("Expected third byte 0x43, got " + third);
        }
    }
}
