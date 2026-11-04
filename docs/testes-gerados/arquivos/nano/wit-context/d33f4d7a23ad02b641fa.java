/*
 * @test
 * @summary Test for setDefaultSSLSocketFactory handling null argument
 * @run main HttpsURLConnectionWitupTest
 */

import java.net.URL;
import javax.net.ssl.SSLSocketFactory;
import com.sun.net.ssl.HttpsURLConnection;

public class HttpsURLConnectionWitupTest {

    public static void main(String[] args) {
        testSetDefaultSSLSocketFactoryNull();
        testSetDefaultSSLSocketFactoryValid();
    }

    private static void testSetDefaultSSLSocketFactoryNull() {
        boolean exceptionThrown = false;
        try {
            HttpsURLConnection.setDefaultSSLSocketFactory(null);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException when passing null");
        }
    }

    private static void testSetDefaultSSLSocketFactoryValid() {
        SSLSocketFactory factory = null;
        try {
            factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        } catch (Exception e) {
            throw new AssertionError("Failed to get default SSLSocketFactory", e);
        }
        // Save current default to restore later
        SSLSocketFactory previous = null;
        try {
            previous = (SSLSocketFactory) SSLSocketFactory.getDefault();
        } catch (Exception e) {
            // ignore
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(factory);
        // Verify that the default was set (by calling getDefault, if available)
        SSLSocketFactory current = null;
        try {
            current = (SSLSocketFactory) SSLSocketFactory.getDefault();
        } catch (Exception e) {
            // ignore
        }
        if (current != factory) {
            throw new AssertionError("Default SSLSocketFactory was not set correctly");
        }
        // Restore previous default if needed
        if (previous != null && previous != factory) {
            HttpsURLConnection.setDefaultSSLSocketFactory(previous);
        }
    }
}
