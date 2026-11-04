/*
 * @test
 * @summary Verify URLConnection.getContent(uc) delegates to uc.getInputStream()
 * @run main URLConnectionWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class URLConnectionWitupTest {

    static final class StubConnection extends URLConnection {
        private final InputStream stream;
        private final IOException failure;
        int calls;

        StubConnection(URL url, InputStream stream, IOException failure) {
            super(url);
            this.stream = stream;
            this.failure = failure;
        }

        @Override
        public void connect() throws IOException {
        }

        @Override
        public InputStream getInputStream() throws IOException {
            calls++;
            if (failure != null) {
                throw failure;
            }
            return stream;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/");
        StubConnection self = new StubConnection(url, null, null);

        // 1. getContent returns the stream produced by uc.getInputStream() and invokes it once
        InputStream expected = new ByteArrayInputStream("abc".getBytes("US-ASCII"));
        StubConnection uc = new StubConnection(url, expected, null);
        Object result = self.getContent(uc);
        check(result == expected, "getContent must return the stream from uc.getInputStream()");
        check(uc.calls == 1, "getInputStream must be called exactly once, was " + uc.calls);

        // 2. The returned stream yields the content of uc
        check(((InputStream) result).read() == 'a', "first byte must be 'a'");

        // 3. IOException thrown by uc.getInputStream() propagates unchanged
        IOException boom = new IOException("boom");
        StubConnection failing = new StubConnection(url, null, boom);
        try {
            self.getContent(failing);
            throw new RuntimeException("expected IOException was not thrown");
        } catch (IOException e) {
            check(e == boom, "propagated IOException must be the same instance");
        }

        // 4. A null uc causes a NullPointerException when dereferenced
        try {
            self.getContent(null);
            throw new RuntimeException("expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
