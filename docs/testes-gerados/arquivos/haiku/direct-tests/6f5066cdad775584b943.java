/*
 * @test
 * @summary Verifies that URL.getContent() delegates to openConnection().getContent() and propagates IOException
 * @run main URLWitupTest
 */

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class URLWitupTest {

    static class FixedConnection extends URLConnection {
        private final Object content;

        FixedConnection(URL url, Object content) {
            super(url);
            this.content = content;
        }

        @Override
        public void connect() {
        }

        @Override
        public Object getContent() throws IOException {
            return content;
        }
    }

    static class TestHandler extends URLStreamHandler {
        int calls;
        final Object content;
        final IOException failure;

        TestHandler(Object content, IOException failure) {
            this.content = content;
            this.failure = failure;
        }

        @Override
        protected URLConnection openConnection(URL u) throws IOException {
            calls++;
            if (failure != null) {
                throw failure;
            }
            return new FixedConnection(u, content);
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        // 1. getContent returns the content produced by the connection
        TestHandler h1 = new TestHandler("payload", null);
        URL u1 = new URL("test", "host", 1, "/a", h1);
        Object o1 = u1.getContent();
        check("payload".equals(o1), "expected payload but got " + o1);
        check(h1.calls == 1, "expected one openConnection call, got " + h1.calls);

        // 2. Each getContent call opens a new connection
        Object o2 = u1.getContent();
        check("payload".equals(o2), "second call returned " + o2);
        check(h1.calls == 2, "expected two openConnection calls, got " + h1.calls);

        // 3. A null content from the connection is returned as null
        TestHandler h3 = new TestHandler(null, null);
        URL u3 = new URL("test", "host", 1, "/b", h3);
        check(u3.getContent() == null, "expected null content");

        // 4. IOException from openConnection propagates out of getContent
        TestHandler h4 = new TestHandler("x", new IOException("boom"));
        URL u4 = new URL("test", "host", 1, "/c", h4);
        boolean thrown = false;
        try {
            u4.getContent();
        } catch (IOException e) {
            thrown = "boom".equals(e.getMessage());
        }
        check(thrown, "expected IOException with message boom");
        check(h4.calls == 1, "expected one openConnection call on failure path");
    }
}
