/*
 * @test
 * @summary Witup-derived checks for InternalLocaleBuilder.setUnicodeLocaleKeyword
 * @modules java.base/sun.util.locale
 * @run main InternalLocaleBuilderWitupTest
 */

import sun.util.locale.InternalLocaleBuilder;
import sun.util.locale.LocaleSyntaxException;

public class InternalLocaleBuilderWitupTest {

    private static int failures = 0;

    public static void main(String[] args) throws Exception {
        rejectsKey("");
        rejectsKey("a");
        rejectsKey("abc");

        acceptsValid("ca", "japanese");
        acceptsValid("ca", "");
        acceptsValid("ca", null);

        rejectsTypeWithIndex("ca", "a", 0);
        rejectsTypeWithIndex("ca", "abc-d", 4);

        InternalLocaleBuilder b = new InternalLocaleBuilder();
        InternalLocaleBuilder r = b.setUnicodeLocaleKeyword("ca", "buddhist");
        if (r != b) {
            failures++;
            System.err.println("setUnicodeLocaleKeyword did not return this");
        }

        if (failures > 0) {
            throw new RuntimeException(failures + " failure(s)");
        }
    }

    private static void rejectsKey(String key) {
        try {
            new InternalLocaleBuilder().setUnicodeLocaleKeyword(key, "abc");
            failures++;
            System.err.println("Invalid key accepted: '" + key + "'");
        } catch (LocaleSyntaxException e) {
            // expected
        }
    }

    private static void acceptsValid(String key, String type) {
        try {
            InternalLocaleBuilder b = new InternalLocaleBuilder();
            InternalLocaleBuilder r = b.setUnicodeLocaleKeyword(key, type);
            if (r != b) {
                failures++;
                System.err.println("Chaining broken for key '" + key + "'");
            }
        } catch (LocaleSyntaxException e) {
            failures++;
            System.err.println("Valid input rejected: key='" + key + "' type='" + type + "'");
        }
    }

    private static void rejectsTypeWithIndex(String key, String type, int expectedIndex) {
        try {
            new InternalLocaleBuilder().setUnicodeLocaleKeyword(key, type);
            failures++;
            System.err.println("Invalid type accepted: '" + type + "'");
        } catch (LocaleSyntaxException e) {
            if (e.getErrorIndex() != expectedIndex) {
                failures++;
                System.err.println("Wrong error index for '" + type + "': " + e.getErrorIndex());
            }
        }
    }
}
