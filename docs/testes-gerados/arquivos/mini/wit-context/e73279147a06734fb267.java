/*
 * @test
 * @summary Test java.net.URLConnection.setAllowUserInteraction(boolean) for IllegalStateException when connected
 * @run main URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import java.lang.reflect.Field;

public class URLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy URLConnection subclass to test protected constructor and allowUserInteraction
        class DummyURLConnection extends URLConnection {
            boolean connectedFlag = false;
            DummyURLConnection(URL url) {
                super(url);
            }
            @Override
            public void connect() {
                connectedFlag = true;
                connected = true;
            }
        }

        URL url = new URL("http://example.com");
        DummyURLConnection conn = new DummyURLConnection(url);

        // Initially not connected, should allow setAllowUserInteraction without exception
        conn.setAllowUserInteraction(true);
        if (!conn.getAllowUserInteraction()) {
            throw new AssertionError("allowUserInteraction should be true after setAllowUserInteraction(true)");
        }

        // Use reflection to set connected to true to simulate connected state
        Field connectedField = URLConnection.class.getDeclaredField("connected");
        connectedField.setAccessible(true);
        connectedField.setBoolean(conn, true);

        // Now setAllowUserInteraction should throw IllegalStateException
        boolean threw = false;
        try {
            conn.setAllowUserInteraction(false);
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalStateException when calling setAllowUserInteraction after connected");
        }
    }
}
