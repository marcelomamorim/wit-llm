/*
 * @test
 * @summary Test sun.security.util.LocalizedMessage.getLocalized method
 * @run main LocalizedMessageWitupTest
 */

import sun.security.util.LocalizedMessage;

public class LocalizedMessageWitupTest {
    public static void main(String[] args) throws Exception {
        testGetLocalizedWithNullArguments();
        testGetLocalizedWithNoArguments();
        testGetLocalizedWithArguments();
    }

    private static void testGetLocalizedWithNullArguments() {
        // Passing null as arguments should return the raw string from ResourcesMgr.getString
        // We cannot mock ResourcesMgr, so test with a known key that returns a string without format
        String key = "test.key.noargs";
        // We expect getLocalized to return the string from ResourcesMgr.getString(key) when arguments is null
        // Since we cannot control ResourcesMgr, we test that the method does not throw and returns non-null
        String result = LocalizedMessage.getLocalized(key, (Object[]) null);
        if (result == null) {
            throw new RuntimeException("getLocalized returned null for null arguments");
        }
    }

    private static void testGetLocalizedWithNoArguments() {
        // Passing no arguments (empty varargs) should format the string with no replacements
        String key = "test.key.noargs";
        String result = LocalizedMessage.getLocalized(key);
        if (result == null) {
            throw new RuntimeException("getLocalized returned null for empty arguments");
        }
    }

    private static void testGetLocalizedWithArguments() {
        // Passing arguments should format the string accordingly
        // Use a key that expects arguments, e.g. "test.key.args" with value "Hello {0} {1}"
        String key = "test.key.args";
        Object[] args = new Object[] {"World", 123};
        String result = LocalizedMessage.getLocalized(key, args);
        if (result == null) {
            throw new RuntimeException("getLocalized returned null for arguments");
        }
        // The result should contain the string representations of the arguments
        if (!result.contains("World") || !result.contains("123")) {
            throw new RuntimeException("getLocalized did not format arguments correctly: " + result);
        }
    }
}
