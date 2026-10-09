/* @test
   @summary Verifies that setRequestProperty throws IllegalStateException if called after connection is established.
   @run main HttpURLConnectionWitupTest_Pe0f345c31121
 */
import java.util.Objects;
public class HttpURLConnectionWitupTest_Pe0f345c31121 {
    public static void main(String[] args) {
        try {
            java.net.URL url = new java.net.URL("http://example.com");
            sun.net.www.protocol.http.HttpURLConnection conn = new sun.net.www.protocol.http.HttpURLConnection(url);
            conn.connect();
            // Attempt to set request property after connection
            try {
                conn.setRequestProperty("Test-Header", "value");
                throw new AssertionError("Expected IllegalStateException not thrown");
            } catch (IllegalStateException e) {
                // Expected exception
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
