/*
 * @test
 * @summary Witup tests for InternalLocaleBuilder.removeUnicodeLocaleAttribute
 * @modules java.base/sun.util.locale
 * @run main InternalLocaleBuilderWitupTest
 */

import sun.util.locale.InternalLocaleBuilder;
import sun.util.locale.LocaleSyntaxException;

public class InternalLocaleBuilderWitupTest {

    public static void main(String[] args) throws Exception {
        testNullAttributeRejected();
        testTooShortAttributeRejected();
        testTooLongAttributeRejected();
        testValidAttributeReturnsSameBuilder();
        testRemoveOnFreshBuilderIsNoop();
        System.out.println("PASS");
    }

    static void testNullAttributeRejected() {
        expectSyntaxException(null);
    }

    static void testTooShortAttributeRejected() {
        expectSyntaxException("a");
    }

    static void testTooLongAttributeRejected() {
        expectSyntaxException("abcdefghi");
    }

    static void testValidAttributeReturnsSameBuilder() throws LocaleSyntaxException {
        InternalLocaleBuilder b = new InternalLocaleBuilder();
        InternalLocaleBuilder r = b.removeUnicodeLocaleAttribute("abc");
        if (r != b) {
            throw new RuntimeException("removeUnicodeLocaleAttribute must return this builder");
        }
    }

    static void testRemoveOnFreshBuilderIsNoop() throws LocaleSyntaxException {
        InternalLocaleBuilder b = new InternalLocaleBuilder();
        InternalLocaleBuilder r = b.removeUnicodeLocaleAttribute("12345678");
        if (r != b) {
            throw new RuntimeException("Expected same builder on no-op removal");
        }
    }

    static void expectSyntaxException(String attr) {
        InternalLocaleBuilder b = new InternalLocaleBuilder();
        try {
            b.removeUnicodeLocaleAttribute(attr);
        } catch (LocaleSyntaxException e) {
            return;
        }
        throw new RuntimeException("Expected LocaleSyntaxException for attribute: " + attr);
    }
}
