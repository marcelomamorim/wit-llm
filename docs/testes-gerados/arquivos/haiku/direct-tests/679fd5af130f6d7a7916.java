/*
 * @test
 * @summary Verify URLConnection.getRequestProperties returns a non-null empty map for a fresh connection
 * @run main URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class URLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/");
        URLConnection c = new URLConnection(url) {
            @Override
            public void connect() {
            }
        };

        Map<String, List<String>> props = c.getRequestProperties();
        if (props == null) {
            throw new RuntimeException("getRequestProperties returned null");
        }
        if (!props.isEmpty()) {
            throw new RuntimeException("Expected empty map for fresh connection, got: " + props);
        }

        Map<String, List<String>> again = c.getRequestProperties();
        if (again == null || !again.isEmpty()) {
            throw new RuntimeException("Second call did not return an empty map");
        }
        if (!props.equals(again)) {
            throw new RuntimeException("Repeated calls returned different maps");
        }

        URLConnection other = new URLConnection(url) {
            @Override
            public void connect() {
            }
        };
        if (!other.getRequestProperties().isEmpty()) {
            throw new RuntimeException("Second instance should also have empty request properties");
        }
    }
}
