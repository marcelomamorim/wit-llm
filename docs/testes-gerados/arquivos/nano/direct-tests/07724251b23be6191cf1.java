/* @test    @summary Test for Charset.isSupported method to verify support detection for charset names.    @run main CharsetWitupTest */

import java.nio.charset.Charset;

public class CharsetWitupTest {

    public static void main(String[] args) {
        testSupportedCharset("UTF-8", true);
        testSupportedCharset("ISO-8859-1", true);
        testSupportedCharset("unsupported-charset", false);
        testSupportedCharset("US-ASCII", true);
        testSupportedCharset("", false);
        testSupportedCharset(null, false);
    }

    private static void testSupportedCharset(String charsetName, boolean expectedSupport) {
        boolean result;
        try {
            result = Charset.isSupported(charsetName);
        } catch (NullPointerException e) {
            result = false;
        }
        if (result != expectedSupport) {
            throw new AssertionError("Charset.isSupported(\"" + charsetName + "\") expected: " + expectedSupport + " but was: " + result);
        }
    }
}
