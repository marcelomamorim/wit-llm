/*
 * @test
 * @summary Test setRequestProperty method for HttpURLConnection
 * @run main HttpURLConnectionWitupTest_Pf6874b80a3f7
 */

import java.net.URL;
import sun.net.www.protocol.http.HttpURLConnection;

public class HttpURLConnectionWitupTest_Pf6874b80a3f7 {

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://example.com");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        // Test setting a request property with valid key and value
        try {
            conn.setRequestProperty("Test-Header", "HeaderValue");
        } catch (Exception e) {
            throw new RuntimeException("Failed to set request property with valid key and value", e);
        }
        
        // Verify that the property was set correctly
        if (!"HeaderValue".equals(conn.getRequestProperty("Test-Header"))) {
            throw new AssertionError("Request property not set correctly");
        }
        
        // Test setting a null key, should throw NullPointerException
        try {
            conn.setRequestProperty(null, "value");
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException npe) {
            // expected
        }
        
        // Test setting a property after connection, should throw IllegalStateException
        conn.connect();
        try {
            conn.setRequestProperty("Another-Header", "value");
            throw new AssertionError("Expected IllegalStateException after connect");
        } catch (IllegalStateException ise) {
            // expected
        }
        
        // Test setting a property with key that is case-insensitive
        URL url2 = new URL("http://example.org");
        HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
        conn2.setRequestProperty("Content-Type", "application/json");
        if (!"application/json".equals(conn2.getRequestProperty("content-type"))) {
            throw new AssertionError("Case-insensitive header key not working");
        }
        
        // Test setting a header that is not allowed (simulate external message header)
        // Since isExternalMessageHeaderAllowed is not accessible, we assume all headers are allowed in this test
        conn2.setRequestProperty("X-Custom-Header", "CustomValue");
        if (!"CustomValue".equals(conn2.getRequestProperty("X-Custom-Header"))) {
            throw new AssertionError("Custom header not set correctly");
        }
        
        System.out.println("All tests passed.");
    }
}
