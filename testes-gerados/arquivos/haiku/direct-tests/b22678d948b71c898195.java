/*
 * @test
 * @summary Tests setUnicodeLocaleKeyword validation and chaining in InternalLocaleBuilder
 * @modules java.base/sun.util.locale
 * @run main InternalLocaleBuilderWitupTest
 */

import sun.util.locale.InternalLocaleBuilder;
import sun.util.locale.LocaleSyntaxException;

public class InternalLocaleBuilderWitupTest {

    public static void main(String[] args) throws Exception {
        testReturnsSameInstanceForValidKeyword();
        testNullTypeAccepted();
        testEmptyTypeAccepted();
        testMultiSubtagTypeAccepted();
        testInvalidKeyRejected();
        testInvalidTypeRejected();
    }

    static void testReturnsSameInstanceForValidKeyword() throws Exception {
        InternalLocaleBuilder b = new InternalLocaleBuilder();
        InternalLocaleBuilder r = b.setUnicodeLocaleKeyword("ca", "gregory");
        if (r != b) {
            throw new RuntimeException("expected same builder instance");
        }
    }

    static void testNullTypeAccepted() throws Exception {
        InternalLocaleBuilder b = new InternalLocaleBuilder();
        b.setUnicodeLocaleKeyword("ca", "gregory");
        InternalLocaleBuilder r = b.setUnicodeLocaleKeyword("ca", null);
        if (r != b) {
            throw new RuntimeException("null type should return builder");
        }
    }

    static void testEmptyTypeAccepted() throws Exception {
        InternalLocaleBuilder b = new InternalLocaleBuilder();
        InternalLocaleBuilder r = b.setUnicodeLocaleKeyword("ca", "");
        if (r != b) {
            throw new RuntimeException("empty type should return builder");
        }
    }

    static void testMultiSubtagTypeAccepted() throws Exception {
        InternalLocaleBuilder b = new InternalLocaleBuilder();
        InternalLocaleBuilder r = b.setUnicodeLocaleKeyword("ca", "gregory_buddhist");
        if (r != b) {
            throw new RuntimeException("underscore-separated type should be accepted");
        }
        r = b.setUnicodeLocaleKeyword("co", "phonebk-abc");
        if (r != b) {
            throw new RuntimeException("hyphen-separated type should be accepted");
        }
    }

    static void testInvalidKeyRejected() {
        InternalLocaleBuilder b = new InternalLocaleBuilder();
        String[] badKeys = {"c", "abc", ""};
        for (String key : badKeys) {
            try {
                b.setUnicodeLocaleKeyword(key, "gregory");
                throw new RuntimeException("expected LocaleSyntaxException for key: '" + key + "'");
            } catch (LocaleSyntaxException e) {
                // expected
            }
        }
    }

    static void testInvalidTypeRejected() {
        InternalLocaleBuilder b = new InternalLocaleBuilder();
        String[] badTypes = {"a", "abcdefghij", "gregory-abcdefghij"};
        for (String type : badTypes) {
            try {
                b.setUnicodeLocaleKeyword("ca", type);
                throw new RuntimeException("expected LocaleSyntaxException for type: '" + type + "'");
            } catch (LocaleSyntaxException e) {
                // expected
            }
        }
    }
}
