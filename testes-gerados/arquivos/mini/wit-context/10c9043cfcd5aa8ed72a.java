/*
 * @test
 * @summary Test sun.net.www.protocol.ftp.FtpURLConnection.setRequestProperty behavior
 * @run main FtpURLConnectionWitupTest
 */

import java.net.URL;
import sun.net.www.protocol.ftp.FtpURLConnection;

public class FtpURLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy URL for constructor
        URL url = new URL("ftp://localhost/");
        FtpURLConnection con = new FtpURLConnection(url);

        // Test valid 'type' property values
        con.setRequestProperty("type", "i");
        con.setRequestProperty("type", "I");
        con.setRequestProperty("type", "a");
        con.setRequestProperty("type", "A");
        con.setRequestProperty("type", "d");
        con.setRequestProperty("type", "D");

        // Test other keys do not throw
        con.setRequestProperty("otherKey", "someValue");

        // Test invalid 'type' value throws IllegalArgumentException
        boolean threw = false;
        try {
            con.setRequestProperty("type", "x");
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalArgumentException for invalid 'type' value");
        }

        // Test null key allowed when not connected (default connected is false)
        // Should not throw NullPointerException
        con.setRequestProperty(null, "value");

        // Simulate connected = true by reflection to test IllegalAccessError path
        // The method throws IllegalAccessError if connected is true (inherited from URLConnection)
        // connected is protected boolean in URLConnection
        java.lang.reflect.Field connectedField = URLConnection.class.getDeclaredField("connected");
        connectedField.setAccessible(true);
        connectedField.setBoolean(con, true);

        threw = false;
        try {
            con.setRequestProperty("anyKey", "anyValue");
        } catch (IllegalAccessError e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalAccessError when connected is true");
        }

        System.out.println("All tests passed.");
    }
}
