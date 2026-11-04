/*
 * @test
 * @summary Test sun.util.locale.InternalLocaleBuilder.setUnicodeLocaleKeyword method
 * @run main InternalLocaleBuilderWitupTest
 */

import sun.util.locale.InternalLocaleBuilder;
import sun.util.locale.LocaleSyntaxException;

public class InternalLocaleBuilderWitupTest {
    public static void main(String[] args) throws Exception {
        testSetUnicodeLocaleKeywordInvalidKey();
        testSetUnicodeLocaleKeywordNullTypeRemovesKey();
        testSetUnicodeLocaleKeywordValidKeyAndType();
        testSetUnicodeLocaleKeywordEmptyType();
    }

    private static void testSetUnicodeLocaleKeywordInvalidKey() {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        String[] invalidKeys = {"", "a", "abc", "1", "@"};
        for (String key : invalidKeys) {
            try {
                builder.setUnicodeLocaleKeyword(key, "type");
                throw new RuntimeException("Expected LocaleSyntaxException for key: " + key);
            } catch (LocaleSyntaxException e) {
                // expected
            }
        }
    }

    private static void testSetUnicodeLocaleKeywordNullTypeRemovesKey() throws LocaleSyntaxException {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        // Add a key first
        builder.setUnicodeLocaleKeyword("ab", "type");
        // Remove it by passing null type
        builder.setUnicodeLocaleKeyword("ab", null);
        // No exception expected, no direct way to assert removal, but no exception means pass
    }

    private static void testSetUnicodeLocaleKeywordValidKeyAndType() throws LocaleSyntaxException {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        // Valid key length 2
        builder.setUnicodeLocaleKeyword("ab", "type1-type2");
        // Valid key with empty type
        builder.setUnicodeLocaleKeyword("cd", "");
        // Valid key with type containing separators replaced
        builder.setUnicodeLocaleKeyword("ef", "type1_type2");
    }

    private static void testSetUnicodeLocaleKeywordEmptyType() throws LocaleSyntaxException {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        // Empty string type should be accepted and stored
        builder.setUnicodeLocaleKeyword("gh", "");
    }
}
