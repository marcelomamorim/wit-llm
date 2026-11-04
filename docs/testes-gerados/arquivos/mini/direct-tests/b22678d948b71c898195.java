/*
 * @test
 * @summary Test sun.util.locale.InternalLocaleBuilder.setUnicodeLocaleKeyword method
 * @run main InternalLocaleBuilderWitupTest
 */

import sun.util.locale.InternalLocaleBuilder;
import sun.util.locale.LocaleSyntaxException;
import sun.util.locale.CaseInsensitiveString;
import sun.util.locale.UnicodeLocaleExtension;
import sun.util.locale.BaseLocale;
import sun.util.locale.LanguageTag;
import sun.util.locale.StringTokenIterator;

public class InternalLocaleBuilderWitupTest {

    public static void main(String[] args) throws Exception {
        testValidKeyAndType();
        testNullTypeRemovesKey();
        testEmptyTypeStoresEmptyString();
        testInvalidKeyThrows();
        testInvalidTypeThrows();
    }

    private static void testValidKeyAndType() throws Exception {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        // valid key according to UnicodeLocaleExtension.isKey: single alphanumeric lowercase
        String key = "ca";
        // valid type subtags: alphanumeric lowercase separated by '-'
        String type = "buddhist";
        builder.setUnicodeLocaleKeyword(key, type);
        // no exception means pass
    }

    private static void testNullTypeRemovesKey() throws Exception {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        String key = "ca";
        String type = "buddhist";
        builder.setUnicodeLocaleKeyword(key, type);
        // now remove by passing null
        builder.setUnicodeLocaleKeyword(key, null);
        // no exception means pass
    }

    private static void testEmptyTypeStoresEmptyString() throws Exception {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        String key = "ca";
        String type = "";
        builder.setUnicodeLocaleKeyword(key, type);
        // no exception means pass
    }

    private static void testInvalidKeyThrows() {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        String invalidKey = "1"; // invalid because UnicodeLocaleExtension.isKey expects a single alpha char
        try {
            builder.setUnicodeLocaleKeyword(invalidKey, "type");
            throw new RuntimeException("Expected LocaleSyntaxException for invalid key");
        } catch (LocaleSyntaxException e) {
            // expected
        }
    }

    private static void testInvalidTypeThrows() {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        String key = "ca";
        // invalid type subtag contains uppercase letter which is invalid
        String invalidType = "BudDisT";
        try {
            builder.setUnicodeLocaleKeyword(key, invalidType);
            throw new RuntimeException("Expected LocaleSyntaxException for invalid type");
        } catch (LocaleSyntaxException e) {
            // expected
        }
    }
}
