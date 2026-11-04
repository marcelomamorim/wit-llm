/*
 * @test
 * @summary Test sun.net.www.URLConnection.addRequestProperty behavior and exceptions
 * @run main URLConnectionWitupTest
 */

import sun.net.www.URLConnection;
import java.net.URL;

public class URLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        testAddRequestPropertyThrowsIllegalStateExceptionIfConnected();
        testAddRequestPropertyThrowsNullPointerExceptionIfKeyNull();
        testAddRequestPropertyNoExceptionIfValidAndNotConnected();
    }

    private static void testAddRequestPropertyThrowsIllegalStateExceptionIfConnected() throws Exception {
        URL url = new URL("http://example.com");
        URLConnection uc = new URLConnection(url) {
            @Override
            public void connect() {}
        };
        // forcibly set connected to true via subclass since connected is protected in java.net.URLConnection
        // connected is protected boolean in java.net.URLConnection
        // Use reflection to set connected to true since no public setter
        java.lang.reflect.Field connectedField = java.net.URLConnection.class.getDeclaredField("connected");
        connectedField.setAccessible(true);
        connectedField.setBoolean(uc, true);

        boolean threw = false;
        try {
            uc.addRequestProperty("key", "value");
        } catch (IllegalStateException e) {
            threw = true;
            if (!"Already connected".equals(e.getMessage())) {
                throw new AssertionError("IllegalStateException message mismatch");
            }
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalStateException not thrown when connected is true");
        }
    }

    private static void testAddRequestPropertyThrowsNullPointerExceptionIfKeyNull() throws Exception {
        URL url = new URL("http://example.com");
        URLConnection uc = new URLConnection(url) {
            @Override
            public void connect() {}
        };
        // connected is false by default
        boolean threw = false;
        try {
            uc.addRequestProperty(null, "value");
        } catch (NullPointerException e) {
            threw = true;
            if (!"key is null".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch");
            }
        }
        if (!threw) {
            throw new AssertionError("Expected NullPointerException not thrown when key is null");
        }
    }

    private static void testAddRequestPropertyNoExceptionIfValidAndNotConnected() throws Exception {
        URL url = new URL("http://example.com");
        URLConnection uc = new URLConnection(url) {
            @Override
            public void connect() {}
        };
        // connected is false by default
        // Should not throw any exception
        uc.addRequestProperty("key", "value");
    }
}
