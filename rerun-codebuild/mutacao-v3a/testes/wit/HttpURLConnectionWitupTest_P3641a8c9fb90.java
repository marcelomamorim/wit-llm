/*
 * @test
 * @summary Tests for HttpURLConnection.setReadTimeout method, including exception scenarios.
 * @run main HttpURLConnectionWitupTest_P3641a8c9fb90
 */

import sun.net.www.protocol.http.HttpURLConnection;

public class HttpURLConnectionWitupTest_P3641a8c9fb90 {

    public static void main(String[] args) {
        testSetReadTimeoutNegative();
        testSetReadTimeoutValid();
    }

    private static void testSetReadTimeoutNegative() {
        try {
            HttpURLConnection connection = createHttpURLConnection();
            connection.setReadTimeout(-1);
            System.out.println("FAILED: Expected IllegalArgumentException for negative timeout");
        } catch (IllegalArgumentException e) {
            System.out.println("PASSED: Caught expected IllegalArgumentException for negative timeout");
        } catch (Exception e) {
            System.out.println("FAILED: Unexpected exception: " + e);
        }
    }

    private static void testSetReadTimeoutValid() {
        try {
            HttpURLConnection connection = createHttpURLConnection();
            int validTimeout = 1000;
            connection.setReadTimeout(validTimeout);
            System.out.println("PASSED: setReadTimeout accepted valid value");
        } catch (Exception e) {
            System.out.println("FAILED: Unexpected exception for valid timeout: " + e);
        }
    }

    private static HttpURLConnection createHttpURLConnection() {
        try {
            // Using a dummy URL; the actual connection isn't established for this test
            java.net.URL url = new java.net.URL("http://example.com");
            return new HttpURLConnection(url, (sun.net.www.protocol.http.Handler) null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create HttpURLConnection", e);
        }
    }
}
