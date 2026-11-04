/*
 * @test
 * @summary Test sun.net.www.URLConnection.setRequestProperty behavior
 * @run main URLConnectionWitupTest
 */

import sun.net.www.URLConnection;
import java.net.URL;
import java.net.MalformedURLException;

public class URLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetRequestPropertyBeforeConnect();
        testSetRequestPropertyNullKey();
        testSetRequestPropertyAfterConnect();
    }

    private static void testSetRequestPropertyBeforeConnect() throws Exception {
        URL url = new URL("http://example.com");
        URLConnection uc = new URLConnection(url) {
            @Override
            public void connect() {
                connected = true;
            }
        };
        uc.setRequestProperty("Test-Key", "Test-Value");
        String val = uc.getProperties().findValue("Test-Key");
        if (!"Test-Value".equals(val)) {
            throw new RuntimeException("setRequestProperty did not set the property correctly");
        }
    }

    private static void testSetRequestPropertyNullKey() throws Exception {
        URL url = new URL("http://example.com");
        URLConnection uc = new URLConnection(url) {
            @Override
            public void connect() {
                connected = true;
            }
        };
        boolean npeThrown = false;
        try {
            uc.setRequestProperty(null, "value");
        } catch (NullPointerException e) {
            if ("key cannot be null".equals(e.getMessage())) {
                npeThrown = true;
            } else {
                throw e;
            }
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException for null key");
        }
    }

    private static void testSetRequestPropertyAfterConnect() throws Exception {
        URL url = new URL("http://example.com");
        URLConnection uc = new URLConnection(url) {
            @Override
            public void connect() {
                connected = true;
            }
        };
        uc.connect();
        boolean iaeThrown = false;
        try {
            uc.setRequestProperty("key", "value");
        } catch (IllegalAccessError e) {
            if ("Already connected".equals(e.getMessage())) {
                iaeThrown = true;
            } else {
                throw e;
            }
        }
        if (!iaeThrown) {
            throw new RuntimeException("Expected IllegalAccessError when setting property after connect");
        }
    }
}
