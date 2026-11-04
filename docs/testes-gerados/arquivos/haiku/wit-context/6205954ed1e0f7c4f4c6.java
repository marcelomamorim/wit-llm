/*
 * @test
 * @summary WITUP generated checks for HttpsURLConnectionImpl.setFixedLengthStreamingMode via public URL.openConnection() API
 * @run main HttpsURLConnectionImplWitupTest
 */

import java.net.HttpURLConnection;
import java.net.URL;

public class HttpsURLConnectionImplWitupTest {

    public static void main(String[] args) throws Exception {
        testValidContentLengthAccepted();
        testNegativeContentLengthRejected();
        testFixedLengthAfterChunkedRejected();
    }

    static HttpURLConnection newConnection() throws Exception {
        return (HttpURLConnection) new URL("https://example.com/resource").openConnection();
    }

    static void testValidContentLengthAccepted() throws Exception {
        HttpURLConnection c = newConnection();
        c.setFixedLengthStreamingMode(0);
        c = newConnection();
        c.setFixedLengthStreamingMode(1024);
    }

    static void testNegativeContentLengthRejected() throws Exception {
        boolean thrown = false;
        try {
            newConnection().setFixedLengthStreamingMode(-1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for negative contentLength");
        }
    }

    static void testFixedLengthAfterChunkedRejected() throws Exception {
        HttpURLConnection c = newConnection();
        c.setChunkedStreamingMode(16);
        boolean thrown = false;
        try {
            c.setFixedLengthStreamingMode(10);
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException when chunked streaming mode is already set");
        }
    }
}
