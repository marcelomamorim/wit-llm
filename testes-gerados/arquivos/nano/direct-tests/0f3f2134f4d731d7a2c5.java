/*
 * @test
 * @summary Test ResourceBundle.getTimeToLive for null arguments and default return value
 * @run main ResourceBundleWitupTest
 */

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleWitupTest {

    public static void main(String[] args) {
        testGetTimeToLiveNullArguments();
        testGetTimeToLiveDefault();
    }

    static void testGetTimeToLiveNullArguments() {
        ResourceBundle rb = ResourceBundle.getBundle("java.util.ResourceBundle");
        boolean exceptionThrown = false;
        try {
            rb.getTimeToLive(null, Locale.US);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException for null baseName");
        }
        exceptionThrown = false;
        try {
            rb.getTimeToLive("baseName", null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException for null locale");
        }
    }

    static void testGetTimeToLiveDefault() {
        ResourceBundle rb = ResourceBundle.getBundle("java.util.ResourceBundle");
        long ttl = rb.getTimeToLive("anyBase", Locale.US);
        if (ttl != Long.MAX_VALUE) {
            throw new AssertionError("Expected getTimeToLive to return Long.MAX_VALUE, but got " + ttl);
        }
    }
}
