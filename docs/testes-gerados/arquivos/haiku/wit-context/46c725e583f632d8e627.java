/*
 * @test
 * @summary Verifies URLConnection.getContent(URLConnection) delegates to the argument's getInputStream()
 * @run main URLConnectionWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownServiceException;

public class URLConnectionWitupTest {

    static class StubConnection extends URLConnection {
        private final InputStream in;

        StubConnection(URL url, InputStream in) {
            super(url);
            this.in = in;
        }

        @Override
        public void connect() {
        }

        @Override
        public InputStream getInputStream() throws IOException {
            if (in == null) {
                return super.getInputStream();
            }
            return in;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://example.com/");

        StubConnection defaultConn = new StubConnection(url, null);
        boolean thrown = false;
        try {
            defaultConn.getContent(defaultConn);
        } catch (UnknownServiceException e) {
            thrown = true;
        }
        check(thrown, "expected UnknownServiceException when getInputStream is unsupported");

        InputStream expected = new ByteArrayInputStream("abc".getBytes("US-ASCII"));
        StubConnection streamConn = new StubConnection(url, expected);
        Object content = streamConn.getContent(streamConn);
        check(content == expected, "getContent must return the stream from uc.getInputStream()");

        StubConnection other = new StubConnection(url, expected);
        StubConnection receiver = new StubConnection(url, null);
        Object delegated = receiver.getContent(other);
        check(delegated == expected, "getContent must delegate to the argument connection");
    }
}
