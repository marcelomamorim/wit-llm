/* @test    @summary Test LocalizedMessage.getLocalized method for various keys and arguments    @run main LocalizedMessageWitupTest */

import sun.security.util.LocalizedMessage;

public class LocalizedMessageWitupTest {

    public static void main(String[] args) {
        testGetLocalizedWithNullArguments();
        testGetLocalizedWithEmptyArguments();
        testGetLocalizedWithSingleArgument();
        testGetLocalizedWithMultipleArguments();
        testGetLocalizedWithUnknownKey();
    }

    private static void testGetLocalizedWithNullArguments() {
        String key = "test.key.null";
        String expected = "Test message for null arguments";
        String result = LocalizedMessage.getLocalized(key, (Object[]) null);
        if (!expected.equals(result)) {
            throw new AssertionError("Failed: testGetLocalizedWithNullArguments. Expected: '" + expected + "', but got: '" + result + "'");
        }
    }

    private static void testGetLocalizedWithEmptyArguments() {
        String key = "test.key.empty";
        String expected = "Test message for empty arguments";
        String result = LocalizedMessage.getLocalized(key, new Object[0]);
        if (!expected.equals(result)) {
            throw new AssertionError("Failed: testGetLocalizedWithEmptyArguments. Expected: '" + expected + "', but got: '" + result + "'");
        }
    }

    private static void testGetLocalizedWithSingleArgument() {
        String key = "test.key.single";
        String expected = "Test message with one argument: arg1";
        String result = LocalizedMessage.getLocalized(key, "arg1");
        if (!expected.equals(result)) {
            throw new AssertionError("Failed: testGetLocalizedWithSingleArgument. Expected: '" + expected + "', but got: '" + result + "'");
        }
    }

    private static void testGetLocalizedWithMultipleArguments() {
        String key = "test.key.multiple";
        String expected = "Test message with args: arg1, arg2";
        String result = LocalizedMessage.getLocalized(key, "arg1", "arg2");
        if (!expected.equals(result)) {
            throw new AssertionError("Failed: testGetLocalizedWithMultipleArguments. Expected: '" + expected + "', but got: '" + result + "'");
        }
    }

    private static void testGetLocalizedWithUnknownKey() {
        String key = "unknown.key";
        String expected = ""; // Assuming ResourcesMgr.getString returns empty string for unknown keys
        String result = LocalizedMessage.getLocalized(key);
        if (!expected.equals(result)) {
            throw new AssertionError("Failed: testGetLocalizedWithUnknownKey. Expected: '" + expected + "', but got: '" + result + "'");
        }
    }
}
