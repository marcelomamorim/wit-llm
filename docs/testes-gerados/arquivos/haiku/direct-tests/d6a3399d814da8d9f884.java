/*
 * @test
 * @summary URL.openConnection delegates to the URLStreamHandler and returns its result
 * @run main URLWitupTest
 */

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class URLWitupTest {

    static class TestConnection extends URLConnection {
        TestConnection(URL u) {
            super(u);
        }

        @Override
        public void connect() throws IOException {
        }
    }

    static class RecordingHandler extends URLStreamHandler {
        int calls;
        URL lastContext;
        URLConnection returned;

        @Override
        protected URLConnection openConnection(URL u) throws IOException {
            calls++;
            lastContext = u;
            returned = new TestConnection(u);
            return returned;
        }
    }

    static class FailingHandler extends URLStreamHandler {
        @Override
        protected URLConnection openConnection(URL u) throws IOException {
            throw new IOException("boom");
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        RecordingHandler h = new RecordingHandler();
        URL url = new URL(null, "test://host/path", h);
        check("test".equals(url.getProtocol()), "protocol mismatch");

        URLConnection c = url.openConnection();
        check(c != null, "openConnection returned null");
        check(c == h.returned, "openConnection must return the handler's connection");
        check(h.calls == 1, "handler must be invoked once, was " + h.calls);
        check(h.lastContext == url, "handler must receive the URL instance itself");

        URLConnection c2 = url.openConnection();
        check(c2 == h.returned, "second call must return the handler's new connection");
        check(h.calls == 2, "handler must be invoked on every call, was " + h.calls);

        URL failing = new URL(null, "test://host/other", new FailingHandler());
        boolean thrown = false;
        try {
            failing.openConnection();
        } catch (IOException e) {
            thrown = true;
            check("boom".equals(e.getMessage()), "unexpected message: " + e.getMessage());
        }
        check(thrown, "IOException from handler must propagate");
    }
}
