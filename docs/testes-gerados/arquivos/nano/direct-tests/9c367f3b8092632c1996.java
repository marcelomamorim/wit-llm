/* @test    @summary Test getRequestProperty method of URLConnection when not connected    @run main URLConnectionWitupTest */

import java.net.URL;
import sun.net.www.URLConnection;

public class URLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://example.com");
        URLConnection conn = new URLConnection(url) {
            // No additional implementation needed for abstract class
        };
        String result = conn.getRequestProperty("AnyKey");
        if (result != null) {
            throw new AssertionError("Expected null, but got: " + result);
        }
        System.out.println("Test passed: getRequestProperty returns null when not connected.");
    }
}
