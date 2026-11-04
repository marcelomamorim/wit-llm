/*
 * @test
 * @summary Verify URL.openStream returns the connection input stream and propagates connection exceptions
 * @run main URLWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.UnknownServiceException;

public class URLWitupTest {

    static final class FixedConnection extends URLConnection {
        private final byte[] data;
        private final boolean failInput;

        FixedConnection(URL u, byte[] data, boolean failInput) {
            super(u);
            this.data = data;
            this.failInput = failInput;
        }

        @Override
        public void connect() {
        }

        @Override
        public InputStream getInputStream() throws IOException {
            if (failInput) {
                throw new UnknownServiceException("protocol doesn't support input");
            }
            return new ByteArrayInputStream(data);
        }
    }

    static URLStreamHandler handler(final byte[] data, final boolean failInput) {
        return new URLStreamHandler() {
            @Override
            protected URLConnection openConnection(URL u) {
                return new FixedConnection(u, data, failInput);
            }
        };
    }

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    static void testReturnsConnectionStream() throws IOException {
        byte[] payload = "ok".getBytes("US-ASCII");
        URL url = new URL(null, "witup://host/path", handler(payload, false));
        try (InputStream in = url.openStream()) {
            byte[] read = in.readAllBytes();
            if (read.length != payload.length
                    || read[0] != payload[0] || read[1] != payload[1]) {
                fail("openStream returned unexpected content");
            }
        }
    }

    static void testPropagatesUnknownServiceException() throws IOException {
        URL url = new URL(null, "witup://host/noinput", handler(new byte[0], true));
        try {
            url.openStream();
            fail("expected UnknownServiceException");
        } catch (UnknownServiceException expected) {
            // expected
        }
    }

    static void testMissingFileThrowsIOException() {
        try {
            URL url = new URL("file:/witup-nonexistent-dir-7f3a/none.txt");
            url.openStream().close();
            fail("expected IOException for missing file");
        } catch (IOException expected) {
            // expected
        }
    }

    public static void main(String[] args) throws Exception {
        testReturnsConnectionStream();
        testPropagatesUnknownServiceException();
        testMissingFileThrowsIOException();
    }
}
