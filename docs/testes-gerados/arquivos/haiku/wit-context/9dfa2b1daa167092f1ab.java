/*
 * @test
 * @summary Verifies that URLConnection.getContent(Class[]) propagates the IOException raised by getInputStream() before consulting the content handler
 * @run main URLConnectionWitupTest
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownServiceException;

public class URLConnectionWitupTest {

    static final class StubConnection extends URLConnection {
        private final IOException toThrow;
        int inputStreamCalls;

        StubConnection(URL url, IOException toThrow) {
            super(url);
            this.toThrow = toThrow;
        }

        @Override
        public void connect() throws IOException {
        }

        @Override
        public InputStream getInputStream() throws IOException {
            inputStreamCalls++;
            throw toThrow;
        }
    }

    static RuntimeException fail(String msg) {
        return new RuntimeException(msg);
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/witup");

        StubConnection c1 = new StubConnection(url, new UnknownServiceException("no input"));
        try {
            c1.getContent(new Class<?>[]{String.class});
            throw fail("expected UnknownServiceException");
        } catch (UnknownServiceException expected) {
            if (c1.inputStreamCalls != 1) {
                throw fail("getInputStream must be called exactly once, was " + c1.inputStreamCalls);
            }
        }

        StubConnection c2 = new StubConnection(url, new FileNotFoundException("missing"));
        try {
            c2.getContent(new Class<?>[0]);
            throw fail("expected FileNotFoundException");
        } catch (FileNotFoundException expected) {
            if (c2.inputStreamCalls != 1) {
                throw fail("getInputStream must be called exactly once, was " + c2.inputStreamCalls);
            }
        }
    }
}
