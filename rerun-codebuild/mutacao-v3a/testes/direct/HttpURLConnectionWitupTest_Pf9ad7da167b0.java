/* @test    @summary Test for HttpURLConnection.setRequestProperty method  @run main HttpURLConnectionWitupTest_Pf9ad7da167b0 */

import java.net.URL;
import sun.net.www.protocol.http.HttpURLConnection;

public class HttpURLConnectionWitupTest_Pf9ad7da167b0 {

    public static void main(String[] args) {
        testSetRequestProperty_Normal();
        testSetRequestProperty_AlreadyConnected();
        testSetRequestProperty_NullKey();
    }

    private static void testSetRequestProperty_Normal() {
        try {
            URL url = new URL("http://example.com");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connected = false; // forcibly set to false to simulate not connected
            conn.setRequestProperty("Custom-Header", "Value");
            assert "Value".equals(conn.requests.get("Custom-Header")) : "Header not set properly";
            System.out.println("testSetRequestProperty_Normal passed");
        } catch (Exception e) {
            throw new RuntimeException("testSetRequestProperty_Normal failed", e);
        }
    }

    private static void testSetRequestProperty_AlreadyConnected() {
        try {
            URL url = new URL("http://example.com");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // simulate connected state
            conn.connected = true;
            conn.connecting = false;
            conn.requests.clear();
            conn.userHeaders.clear();
            try {
                conn.setRequestProperty("Header", "Value");
                throw new AssertionError("Expected IllegalStateException not thrown");
            } catch (IllegalStateException e) {
                assert e.getMessage() == null || e.getMessage().isEmpty() : "Unexpected message";
                System.out.println("testSetRequestProperty_AlreadyConnected passed");
            }
        } catch (Exception e) {
            throw new RuntimeException("testSetRequestProperty_AlreadyConnected failed", e);
        }
    }

    private static void testSetRequestProperty_NullKey() {
        try {
            URL url = new URL("http://example.com");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connected = false;
            conn.setRequestProperty(null, "Value");
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("testSetRequestProperty_NullKey passed");
        } catch (Exception e) {
            throw new RuntimeException("testSetRequestProperty_NullKey failed", e);
        }
    }
}
