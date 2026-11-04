/*
 * @test
 * @summary URLConnection.getContent() must call getInputStream() first and propagate its IOException, and return the stream when no content type is available
 * @run main URLConnectionWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class URLConnectionWitupTest {

    static class FailingConnection extends URLConnection {
        int calls = 0;
        FailingConnection(URL url) {
            super(url);
        }
        @Override
        public void connect() throws IOException {
        }
        @Override
        public InputStream getInputStream() throws IOException {
            calls++;
            throw new IOException("expected failure");
        }
    }

    static class StreamConnection extends URLConnection {
        final InputStream stream;
        int calls = 0;
        StreamConnection(URL url, InputStream stream) {
            super(url);
            this.stream = stream;
        }
        @Override
        public void connect() throws IOException {
        }
        @Override
        public InputStream getInputStream() throws IOException {
            calls++;
            return stream;
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/witup/test");

        FailingConnection failing = new FailingConnection(url);
        try {
            failing.getContent();
            throw new RuntimeException("expected IOException was not thrown");
        } catch (IOException expected) {
            if (!"expected failure".equals(expected.getMessage())) {
                throw new RuntimeException("unexpected message: " + expected.getMessage());
            }
        }
        if (failing.calls != 1) {
            throw new RuntimeException("getInputStream not called exactly once: " + failing.calls);
        }

        InputStream in = new ByteArrayInputStream(new byte[] {1, 2, 3});
        StreamConnection conn = new StreamConnection(url, in);
        Object content = conn.getContent();
        if (conn.calls != 1) {
            throw new RuntimeException("getInputStream not called exactly once: " + conn.calls);
        }
        if (content != in) {
            throw new RuntimeException("expected the stream returned by getInputStream when no content type is set");
        }
    }
}
