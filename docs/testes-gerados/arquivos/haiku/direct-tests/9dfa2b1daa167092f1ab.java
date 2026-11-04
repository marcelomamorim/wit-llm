/*
 * @test
 * @summary Verify URLConnection.getContent(Class[]) calls getInputStream first and propagates its IOException
 * @run main URLConnectionWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class URLConnectionWitupTest {

    static class StubConnection extends URLConnection {
        int inputStreamCalls;
        boolean failOnInput;
        final InputStream stream = new ByteArrayInputStream(new byte[] {1, 2, 3});

        StubConnection(URL url) {
            super(url);
        }

        @Override
        public void connect() throws IOException {
        }

        @Override
        public InputStream getInputStream() throws IOException {
            inputStreamCalls++;
            if (failOnInput) {
                throw new IOException("boom");
            }
            return stream;
        }

        @Override
        public String getContentType() {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/resource.bin");

        StubConnection c = new StubConnection(url);
        Object r = c.getContent(new Class<?>[] {InputStream.class});
        if (r != c.stream) {
            throw new RuntimeException("expected getInputStream() result, got " + r);
        }
        if (c.inputStreamCalls != 1) {
            throw new RuntimeException("getInputStream calls: " + c.inputStreamCalls);
        }

        Object none = c.getContent(new Class<?>[] {String.class});
        if (none != null) {
            throw new RuntimeException("expected null for non-matching class, got " + none);
        }
        if (c.inputStreamCalls != 2) {
            throw new RuntimeException("getInputStream calls: " + c.inputStreamCalls);
        }

        StubConnection failing = new StubConnection(url);
        failing.failOnInput = true;
        boolean thrown = false;
        try {
            failing.getContent(new Class<?>[] {InputStream.class});
        } catch (IOException expected) {
            thrown = "boom".equals(expected.getMessage());
        }
        if (!thrown) {
            throw new RuntimeException("IOException from getInputStream was not propagated");
        }
        if (failing.inputStreamCalls != 1) {
            throw new RuntimeException("getInputStream calls: " + failing.inputStreamCalls);
        }
    }
}
