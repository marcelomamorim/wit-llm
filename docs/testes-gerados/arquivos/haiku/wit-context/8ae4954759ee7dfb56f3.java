/*
 * @test
 * @summary Checks public argument validation of HttpURLConnection.setFixedLengthStreamingMode(long) on a fresh, unconnected connection
 * @run main HttpURLConnectionWitupTest
 */

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnectionWitupTest {

    private static int failures = 0;

    private static HttpURLConnection newConnection() throws IOException {
        URL url = new URL("http://localhost/");
        return (HttpURLConnection) url.openConnection();
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    private static void expectIllegalArgument(long contentLength) throws IOException {
        HttpURLConnection c = newConnection();
        try {
            c.setFixedLengthStreamingMode(contentLength);
            check(false, "expected IllegalArgumentException for " + contentLength);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testValidLengthsAccepted() throws IOException {
        HttpURLConnection c = newConnection();
        try {
            c.setFixedLengthStreamingMode(0L);
        } catch (RuntimeException e) {
            check(false, "0 must be accepted: " + e);
        }
        try {
            c.setFixedLengthStreamingMode(1024L);
        } catch (RuntimeException e) {
            check(false, "1024 must be accepted: " + e);
        }
        try {
            c.setFixedLengthStreamingMode(Long.MAX_VALUE);
        } catch (RuntimeException e) {
            check(false, "Long.MAX_VALUE must be accepted: " + e);
        }
    }

    private static void testNegativeLengthsRejected() throws IOException {
        expectIllegalArgument(-1L);
        expectIllegalArgument(Long.MIN_VALUE);
    }

    private static void testInvalidCallDoesNotBlockLaterValidCall() throws IOException {
        HttpURLConnection c = newConnection();
        try {
            c.setFixedLengthStreamingMode(-5L);
            check(false, "expected IllegalArgumentException for -5");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        try {
            c.setFixedLengthStreamingMode(10L);
        } catch (RuntimeException e) {
            check(false, "valid length after rejected one must be accepted: " + e);
        }
    }

    public static void main(String[] args) throws Exception {
        testValidLengthsAccepted();
        testNegativeLengthsRejected();
        testInvalidCallDoesNotBlockLaterValidCall();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
