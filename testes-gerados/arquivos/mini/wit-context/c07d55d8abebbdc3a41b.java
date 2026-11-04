/*
 * @test
 * @summary Test sun.net.www.http.ChunkedInputStream.available() method behavior including exception on closed stream
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
        // Override methods if needed, but no behavior required for this test
    }

    static class DummyMessageHeader extends MessageHeader {
        public DummyMessageHeader() {
            super();
        }
    }

    static class ClosedInputStream extends InputStream {
        @Override
        public int read() throws IOException {
            throw new IOException("Stream closed");
        }
    }

    public static void main(String[] args) throws Exception {
        testAvailableOnOpenStream();
        testAvailableOnClosedStream();
        System.out.println("ChunkedInputStream.available() tests passed.");
    }

    private static void testAvailableOnOpenStream() throws Exception {
        // Provide a simple InputStream with some data
        byte[] data = {0x3, 'a', 'b', 'c', 0x0, '\r', '\n'}; // chunk size 3, data 'abc', last chunk 0
        ByteArrayInputStream bais = new ByteArrayInputStream(data);

        HttpClient hc = new DummyHttpClient();
        MessageHeader mh = new DummyMessageHeader();

        ChunkedInputStream cis = new ChunkedInputStream(bais, hc, mh);

        // Initially available() should be >= 0 and not throw
        int avail = cis.available();
        if (avail < 0) {
            throw new RuntimeException("available() returned negative value on open stream");
        }

        // Read all bytes to exhaust stream
        while (cis.read() != -1) {}

        // After reading all, available() should return 0
        int availAfter = cis.available();
        if (availAfter != 0) {
            throw new RuntimeException("available() after stream end should be 0 but was " + availAfter);
        }

        cis.close();
    }

    private static void testAvailableOnClosedStream() throws Exception {
        // Create a ChunkedInputStream wrapping an InputStream that throws IOException on read
        InputStream closedIS = new ClosedInputStream();
        HttpClient hc = new DummyHttpClient();
        MessageHeader mh = new DummyMessageHeader();

        ChunkedInputStream cis = new ChunkedInputStream(closedIS, hc, mh);

        // Close the ChunkedInputStream to simulate closed state
        cis.close();

        // available() should throw IOException because ensureOpen() throws IOException when closed
        boolean threw = false;
        try {
            cis.available();
        } catch (IOException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("available() did not throw IOException on closed stream");
        }
    }
}
