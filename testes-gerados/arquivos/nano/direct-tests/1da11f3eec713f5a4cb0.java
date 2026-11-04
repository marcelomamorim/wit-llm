/* @test    @summary Test addRequestProperty method of URLConnection for null key and connected state    @run main URLConnectionWitupTest */

import java.net.URL;
import sun.net.www.URLConnection;

public class URLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://example.com");
        TestURLConnection conn = new TestURLConnection(url);
        testAddRequestPropertyNullKey(conn);
        testAddRequestPropertyWhenConnected(conn);
    }

    static void testAddRequestPropertyNullKey(URLConnection conn) {
        try {
            conn.addRequestProperty(null, "value");
            System.out.println("FAIL: Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null key");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static void testAddRequestPropertyWhenConnected(URLConnection conn) {
        try {
            conn.connected = true;
            conn.addRequestProperty("Key", "Value");
            System.out.println("FAIL: Expected IllegalStateException when connected");
        } catch (IllegalStateException e) {
            System.out.println("PASS: IllegalStateException thrown as expected when connected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static class TestURLConnection extends URLConnection {
        boolean connected = false;
        public TestURLConnection(URL u) {
            super(u);
        }
        @Override
        public void connect() {
            // no-op
        }
    }
}
