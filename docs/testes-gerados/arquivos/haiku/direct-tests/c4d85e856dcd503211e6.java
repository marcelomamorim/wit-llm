/*
 * @test
 * @summary Tests InternalLocaleBuilder.addUnicodeLocaleAttribute accepts well-formed attributes and rejects ill-formed ones
 * @modules java.base/sun.util.locale
 * @run main InternalLocaleBuilderWitupTest
 */

import sun.util.locale.InternalLocaleBuilder;
import sun.util.locale.LocaleSyntaxException;

public class InternalLocaleBuilderWitupTest {

    private static int failures = 0;

    public static void main(String[] args) throws Exception {
        testValidAttributeReturnsBuilder();
        testValidMaxLengthAttribute();
        testDuplicateAttributeAccepted();
        testTooShortAttributeRejected();
        testTooLongAttributeRejected();
        testHyphenInAttributeRejected();
        if (failures > 0) {
            throw new RuntimeException(failures + " test(s) failed");
        }
    }

    private static void testValidAttributeReturnsBuilder() throws Exception {
        InternalLocaleBuilder b = new InternalLocaleBuilder();
        InternalLocaleBuilder r = b.addUnicodeLocaleAttribute("abc");
        check(r == b, "addUnicodeLocaleAttribute should return this builder");
    }

    private static void testValidMaxLengthAttribute() throws Exception {
        InternalLocaleBuilder b = new InternalLocaleBuilder();
        InternalLocaleBuilder r = b.addUnicodeLocaleAttribute("ABCDEFGH");
        check(r == b, "8-character attribute should be accepted");
    }

    private static void testDuplicateAttributeAccepted() throws Exception {
        InternalLocaleBuilder b = new InternalLocaleBuilder();
        b.addUnicodeLocaleAttribute("abc");
        InternalLocaleBuilder r = b.addUnicodeLocaleAttribute("ABC");
        check(r == b, "case-variant duplicate attribute should be accepted without error");
    }

    private static void testTooShortAttributeRejected() {
        expectSyntaxException("ab");
    }

    private static void testTooLongAttributeRejected() {
        expectSyntaxException("ABCDEFGHI");
    }

    private static void testHyphenInAttributeRejected() {
        expectSyntaxException("ab-c");
    }

    private static void expectSyntaxException(String attribute) {
        InternalLocaleBuilder b = new InternalLocaleBuilder();
        try {
            b.addUnicodeLocaleAttribute(attribute);
            fail("expected LocaleSyntaxException for attribute: " + attribute);
        } catch (LocaleSyntaxException e) {
            // expected
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            fail(message);
        }
    }

    private static void fail(String message) {
        failures++;
        System.err.println("FAILED: " + message);
    }
}
