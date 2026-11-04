/* @test    @summary Test for sun.security.util.LocalizedMessage.getLocalized(String, Object...)    @run main LocalizedMessageWitupTest */

import sun.security.util.LocalizedMessage;
import sun.security.util.ResourcesMgr;

public class LocalizedMessageWitupTest {

    public static void main(String[] args) {
        testGetLocalizedWithNullArguments();
        testGetLocalizedWithArguments();
    }

    private static void testGetLocalizedWithNullArguments() {
        String key = "some.key";
        String expectedValue = ResourcesMgr.getString(key);
        String result = LocalizedMessage.getLocalized(key, (Object[]) null);
        if (!expectedValue.equals(result)) {
            throw new AssertionError("Expected: " + expectedValue + ", but got: " + result);
        }
    }

    private static void testGetLocalizedWithArguments() {
        String key = "some.other.key";
        String value = ResourcesMgr.getString(key);
        Object[] args = {"arg1", "arg2"};
        java.text.MessageFormat form = new java.text.MessageFormat(value);
        String expected = form.format(args);
        String result = LocalizedMessage.getLocalized(key, args);
        if (!expected.equals(result)) {
            throw new AssertionError("Expected: " + expected + ", but got: " + result);
        }
    }
}
