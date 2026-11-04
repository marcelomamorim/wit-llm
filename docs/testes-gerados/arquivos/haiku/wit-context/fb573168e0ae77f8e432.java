/*
 * @test
 * @summary URLConnection.getContent propagates exceptions thrown by getInputStream before content handling
 * @run main URLConnectionWitupTest
 */

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

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/witup");

        StubConnection unknown = new StubConnection(url,
                new UnknownServiceException("no input"));
        try {
            unknown.getContent();
            throw new RuntimeException("expected UnknownServiceException");
        } catch (UnknownServiceException e) {
            if (!"no input".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
        if (unknown.inputStreamCalls != 1) {
            throw new RuntimeException("getInputStream calls: " + unknown.inputStreamCalls);
        }

        StubConnection generic = new StubConnection(url,
                new IOException("expected-failure"));
        try {
            generic.getContent();
            throw new RuntimeException("expected IOException");
        } catch (UnknownServiceException e) {
            throw new RuntimeException("wrong exception type", e);
        } catch (IOException e) {
            if (!"expected-failure".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
        if (generic.inputStreamCalls != 1) {
            throw new RuntimeException("getInputStream calls: " + generic.inputStreamCalls);
        }
    }
}
