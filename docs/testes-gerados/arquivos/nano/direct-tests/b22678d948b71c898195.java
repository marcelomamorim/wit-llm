/* @test    @summary Test setUnicodeLocaleKeyword method for InternalLocaleBuilder class    @run main InternalLocaleBuilderWitupTest */

import sun.util.locale.InternalLocaleBuilder;
import sun.util.locale.LocaleSyntaxException;
import static org.junit.Assert.*;

public class InternalLocaleBuilderWitupTest {

    public static void main(String[] args) throws Exception {
        testSetUnicodeLocaleKeywordValid();
        testSetUnicodeLocaleKeywordInvalidKey();
        testSetUnicodeLocaleKeywordNullType();
        testSetUnicodeLocaleKeywordEmptyType();
    }

    static void testSetUnicodeLocaleKeywordValid() throws Exception {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        builder.setUnicodeLocaleKeyword("abc", "def");
        // No exception thrown, test passes
    }

    static void testSetUnicodeLocaleKeywordInvalidKey() {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        try {
            builder.setUnicodeLocaleKeyword("InvalidKey!", "value");
            fail("Expected LocaleSyntaxException for invalid key");
        } catch (LocaleSyntaxException e) {
            // Expected exception
        }
    }

    static void testSetUnicodeLocaleKeywordNullType() throws Exception {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        builder.setUnicodeLocaleKeyword("abc", null);
        // No exception, key should be removed if present
    }

    static void testSetUnicodeLocaleKeywordEmptyType() throws Exception {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        builder.setUnicodeLocaleKeyword("abc", "");
        // No exception, key should be set with empty string
    }
}
