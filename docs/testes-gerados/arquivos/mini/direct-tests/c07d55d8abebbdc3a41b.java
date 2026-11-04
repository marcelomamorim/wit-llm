/*
 * @test
 * @summary Test sun.net.www.http.ChunkedInputStream.available() method behavior
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
        public void finished() {}
    }

    static class DummyMessageHeader extends MessageHeader {
        public DummyMessageHeader() {
            super();
        }
    }

    public static void main(String[] args) throws Exception {
        testAvailableReturnsZeroWhenNoData();
        testAvailableReturnsPositiveWhenDataAvailable();
    }

    // Test that available() returns 0 when no chunk data is available
    private static void testAvailableReturnsZeroWhenNoData() throws Exception {
        InputStream emptyStream = new ByteArrayInputStream(new byte[0]);
        DummyHttpClient hc = new DummyHttpClient();
        DummyMessageHeader mh = new DummyMessageHeader();

        ChunkedInputStream cis = new ChunkedInputStream(emptyStream, hc, mh);

        // Initially, chunkCount and chunkPos are zero, so avail = 0
        // readAhead(false) will attempt to read but stream is empty, so returns -1
        int avail = cis.available();

        if (avail != 0) {
            throw new RuntimeException("Expected available() to return 0 when no data, but got " + avail);
        }
    }

    // Test that available() returns positive when chunk data is available
    private static void testAvailableReturnsPositiveWhenDataAvailable() throws Exception {
        // Prepare chunked data: chunk size 4 (hex "4\r\n"), data "test", end chunk "0\r\n\r\n"
        byte[] chunkedData = "4\r\ntest\r\n0\r\n\r\n".getBytes("US-ASCII");
        InputStream chunkedStream = new ByteArrayInputStream(chunkedData);
        DummyHttpClient hc = new DummyHttpClient();
        DummyMessageHeader mh = new DummyMessageHeader();

        ChunkedInputStream cis = new ChunkedInputStream(chunkedStream, hc, mh);

        // Read one byte to trigger chunk processing
        int firstByte = cis.read();
        if (firstByte != 't') {
            throw new RuntimeException("Expected first byte 't' but got " + (char)firstByte);
        }

        // Now available() should return positive (chunkCount - chunkPos > 0)
        int avail = cis.available();
        if (avail <= 0) {
            throw new RuntimeException("Expected available() to return positive when data available, but got " + avail);
        }
    }
}
