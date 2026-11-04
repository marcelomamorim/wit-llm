/*
 * @test
 * @summary Test sun.net.www.http.ChunkedInputStream.read() behavior including IOException on closed stream
 * @run main ChunkedInputStreamWitupTest
 */

import java.io.*;
import sun.net.www.http.*;

public class ChunkedInputStreamWitupTest {

    // Minimal stub classes to satisfy constructor dependencies
    static class DummyHttpClient extends HttpClient {
        public DummyHttpClient() {
            super(null, null, null, null, null, null, null, null, null, null);
        }
    }

    static class DummyMessageHeader extends MessageHeader {
        public DummyMessageHeader() {
            super();
        }
    }

    static class ClosedInputStream extends InputStream {
        private boolean closed = false;
        public void close() {
            closed = true;
        }
        public int read() throws IOException {
            if (closed) {
                throw new IOException("Stream closed");
            }
            return -1;
        }
    }

    public static void main(String[] args) throws Exception {
        testReadReturnsMinusOneOnEmptyStream();
        testReadThrowsIOExceptionWhenClosed();
    }

    private static void testReadReturnsMinusOneOnEmptyStream() throws Exception {
        InputStream emptyStream = new ByteArrayInputStream(new byte[0]);
        HttpClient hc = new DummyHttpClient();
        MessageHeader mh = new DummyMessageHeader();
        ChunkedInputStream cis = new ChunkedInputStream(emptyStream, hc, mh);

        int result = cis.read();
        if (result != -1) {
            throw new RuntimeException("Expected -1 on empty stream, got: " + result);
        }
    }

    private static void testReadThrowsIOExceptionWhenClosed() throws Exception {
        InputStream baseStream = new ByteArrayInputStream(new byte[] {1, 2, 3});
        HttpClient hc = new DummyHttpClient();
        MessageHeader mh = new DummyMessageHeader();
        ChunkedInputStream cis = new ChunkedInputStream(baseStream, hc, mh);

        cis.close();

        boolean threw = false;
        try {
            cis.read();
        } catch (IOException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IOException when reading from closed ChunkedInputStream");
        }
    }
}
