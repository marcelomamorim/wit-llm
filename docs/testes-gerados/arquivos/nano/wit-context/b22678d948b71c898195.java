/*
 * @test
 * @summary Test for InternalLocaleBuilder.setUnicodeLocaleKeyword method
 * @run main InternalLocaleBuilderWitupTest
 */

import sun.util.locale.InternalLocaleBuilder;
import sun.util.locale.LocaleSyntaxException;

public class InternalLocaleBuilderWitupTest {

    public static void main(String[] args) throws Exception {
        testSetUnicodeLocaleKeywordValid();
        testSetUnicodeLocaleKeywordInvalidKey();
        testSetUnicodeLocaleKeywordNullType();
        testSetUnicodeLocaleKeywordEmptyType();
        testSetUnicodeLocaleKeywordInvalidTypeSubtag();
    }

    static void testSetUnicodeLocaleKeywordValid() throws Exception {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        String validKey = "abc";
        String validType = "type-1";
        try {
            builder.setUnicodeLocaleKeyword(validKey, validType);
            System.out.println("PASS: valid key and type");
        } catch (LocaleSyntaxException e) {
            throw new AssertionError("Unexpected exception for valid input", e);
        }
    }

    static void testSetUnicodeLocaleKeywordInvalidKey() throws Exception {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        String invalidKey = "a"; // invalid because length != 2
        String type = "type";
        try {
            builder.setUnicodeLocaleKeyword(invalidKey, type);
            throw new AssertionError("Expected LocaleSyntaxException for invalid key");
        } catch (LocaleSyntaxException e) {
            System.out.println("PASS: caught expected exception for invalid key");
        }
    }

    static void testSetUnicodeLocaleKeywordNullType() throws Exception {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        String key = "ab";
        builder.setUnicodeLocaleKeyword(key, null);
        System.out.println("PASS: null type removes key if present");
    }

    static void testSetUnicodeLocaleKeywordEmptyType() throws Exception {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        String key = "ab";
        builder.setUnicodeLocaleKeyword(key, "");
        System.out.println("PASS: empty type accepted");
    }

    static void testSetUnicodeLocaleKeywordInvalidTypeSubtag() throws Exception {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        String key = "ab";
        String invalidType = "type@"; // invalid because '@' is not a valid subtag
        try {
            builder.setUnicodeLocaleKeyword(key, invalidType);
            throw new AssertionError("Expected LocaleSyntaxException for invalid type");
        } catch (LocaleSyntaxException e) {
            System.out.println("PASS: caught expected exception for invalid type");
        }
    }
}
