/*
 * @test
 * @summary URL.openConnection() returns a fresh connection and delegates to the URL stream handler
 * @run main URLWitupTest
 */
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class URLWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static final class StubConnection extends URLConnection {
        StubConnection(URL u) {
            super(u);
        }

        @Override
        public void connect() throws IOException {
        }
    }

    static final class RecordingHandler extends URLStreamHandler {
        int calls;

        @Override
        protected URLConnection openConnection(URL u) throws IOException {
            calls++;
            return new StubConnection(u);
        }
    }

    public static void main(String[] args) throws Exception {
        URL http = new URL("http://example.com/docs/resource1.html");
        URLConnection c1 = http.openConnection();
        check(c1 != null, "openConnection() must not return null");
        check(c1 instanceof HttpURLConnection, "http URL must yield HttpURLConnection");
        check(c1.getURL().toExternalForm().equals(http.toExternalForm()),
              "connection URL must match the source URL");

        URLConnection c2 = http.openConnection();
        check(c1 != c2, "each openConnection() call must return a new connection");

        URL file = new URL("file:/tmp/witup-openconnection-test.txt");
        URLConnection fc = file.openConnection();
        check(fc != null, "file URL openConnection() must not return null");
        check(fc.getURL().toExternalForm().equals(file.toExternalForm()),
              "file connection URL must match the source URL");

        RecordingHandler handler = new RecordingHandler();
        URL custom = new URL("stubproto", "stubhost", 8080, "/resource", handler);
        URLConnection sc = custom.openConnection();
        check(handler.calls == 1, "openConnection() must delegate exactly once to the handler");
        check(sc instanceof StubConnection, "connection must be the one created by the handler");
    }
}
