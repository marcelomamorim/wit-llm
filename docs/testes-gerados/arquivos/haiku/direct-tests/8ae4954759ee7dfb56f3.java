/*
 * @test
 * @summary Verify argument and state checks of HttpURLConnection.setFixedLengthStreamingMode(long)
 * @run main HttpURLConnectionWitupTest
 */

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnectionWitupTest {

    static final class TestConnection extends HttpURLConnection {
        TestConnection(URL u) {
            super(u);
        }

        @Override
        public void connect() throws IOException {
            connected = true;
        }

        @Override
        public void disconnect() {
        }

        @Override
        public boolean usingProxy() {
            return false;
        }
    }

    interface Action {
        void run() throws Exception;
    }

    static void expectException(Class<? extends Exception> type, Action action) {
        try {
            action.run();
        } catch (Exception e) {
            if (type.isInstance(e)) {
                return;
            }
            throw new RuntimeException("Unexpected exception: " + e, e);
        }
        throw new RuntimeException("Expected " + type.getName());
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/");

        TestConnection zero = new TestConnection(url);
        zero.setFixedLengthStreamingMode(0L);
        check(zero.fixedContentLengthLong == 0L, "zero length not stored");

        TestConnection max = new TestConnection(url);
        max.setFixedLengthStreamingMode(Long.MAX_VALUE);
        check(max.fixedContentLengthLong == Long.MAX_VALUE, "max length not stored");

        TestConnection negative = new TestConnection(url);
        expectException(IllegalArgumentException.class,
                () -> negative.setFixedLengthStreamingMode(-1L));

        TestConnection minNegative = new TestConnection(url);
        expectException(IllegalArgumentException.class,
                () -> minNegative.setFixedLengthStreamingMode(Long.MIN_VALUE));

        TestConnection connectedConn = new TestConnection(url);
        connectedConn.connect();
        expectException(IllegalStateException.class,
                () -> connectedConn.setFixedLengthStreamingMode(10L));

        TestConnection chunked = new TestConnection(url);
        chunked.chunkLength = 16;
        expectException(IllegalStateException.class,
                () -> chunked.setFixedLengthStreamingMode(10L));

        TestConnection connectedAndNegative = new TestConnection(url);
        connectedAndNegative.connect();
        expectException(IllegalStateException.class,
                () -> connectedAndNegative.setFixedLengthStreamingMode(-5L));
    }
}
