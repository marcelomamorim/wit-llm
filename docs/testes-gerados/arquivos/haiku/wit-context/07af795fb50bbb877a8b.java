/*
 * @test
 * @summary Verify observable argument and state checks of HttpsURLConnection.setFixedLengthStreamingMode via public URL.openConnection()
 * @run main HttpsURLConnectionImplWitupTest
 */

import java.net.HttpURLConnection;
import java.net.URL;

public class HttpsURLConnectionImplWitupTest {

    public static void main(String[] args) throws Exception {
        testNegativeLengthRejected();
        testZeroAndPositiveAccepted();
        testChunkedModeRejectsFixedLength();
    }

    static HttpURLConnection open() throws Exception {
        return (HttpURLConnection) new URL("https://localhost/").openConnection();
    }

    static void testNegativeLengthRejected() throws Exception {
        HttpURLConnection c = open();
        try {
            c.setFixedLengthStreamingMode(-1L);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for negative content length");
    }

    static void testZeroAndPositiveAccepted() throws Exception {
        HttpURLConnection c = open();
        c.setFixedLengthStreamingMode(0L);
        c.setFixedLengthStreamingMode(16L);
    }

    static void testChunkedModeRejectsFixedLength() throws Exception {
        HttpURLConnection c = open();
        c.setChunkedStreamingMode(0);
        try {
            c.setFixedLengthStreamingMode(16L);
        } catch (IllegalStateException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalStateException when chunked streaming mode is set");
    }
}
