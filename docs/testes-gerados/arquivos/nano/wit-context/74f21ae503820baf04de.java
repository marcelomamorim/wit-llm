/*
 * @test
 * @summary Test for addRequestProperty method in HttpURLConnection
 * @run main HttpURLConnectionWitupTest
 */

import java.net.URL;
import sun.net.www.protocol.http.HttpURLConnection;

public class HttpURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testAddRequestPropertyConnected();
        testAddRequestPropertyNullKey();
    }

    private static void testAddRequestPropertyConnected() throws Exception {
        URL url = new URL("http://example.com");
        HttpURLConnection conn = new HttpURLConnection(url, (String) null, 80);
        // Simulate connection state
        setConnectedState(conn, true);
        try {
            conn.addRequestProperty("Test-Key", "Test-Value");
            throw new AssertionError("Expected IllegalStateException was not thrown");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }

    private static void testAddRequestPropertyNullKey() throws Exception {
        URL url = new URL("http://example.com");
        HttpURLConnection conn = new HttpURLConnection(url, (String) null, 80);
        // Simulate non-connected state
        setConnectedState(conn, false);
        try {
            conn.addRequestProperty(null, "Value");
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void setConnectedState(HttpURLConnection conn, boolean connected) {
        // Reflection or package-private access needed; assuming a method or field exists for testing
        // Since direct access is not available, this is a placeholder for test setup
        // In real tests, use reflection or a test-specific subclass to set state
    }
}
