/*
 * @test
 * @summary Test setConnectTimeout method for HttpURLConnection
 * @run main HttpURLConnectionWitupTest
 */

import java.net.URL;
import sun.net.www.protocol.http.HttpURLConnection;

public class HttpURLConnectionWitupTest {

    public static void main(String[] args) {
        testSetConnectTimeoutPositive();
        testSetConnectTimeoutZero();
        testSetConnectTimeoutNegative();
    }

    static void testSetConnectTimeoutPositive() {
        try {
            URL url = new URL("http://example.com");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(1000);
            assert conn.connectTimeout == 1000 : "Expected connectTimeout to be 1000";
        } catch (Exception e) {
            throw new RuntimeException("Failed testSetConnectTimeoutPositive", e);
        }
    }

    static void testSetConnectTimeoutZero() {
        try {
            URL url = new URL("http://example.com");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(0);
            assert conn.connectTimeout == 0 : "Expected connectTimeout to be 0";
        } catch (Exception e) {
            throw new RuntimeException("Failed testSetConnectTimeoutZero", e);
        }
    }

    static void testSetConnectTimeoutNegative() {
        try {
            URL url = new URL("http://example.com");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative timeout");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Failed testSetConnectTimeoutNegative", e);
        }
    }
}
