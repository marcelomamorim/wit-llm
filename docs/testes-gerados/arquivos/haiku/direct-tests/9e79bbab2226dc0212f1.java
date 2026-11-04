/*
 * @test
 * @summary Tests Checks.requireServiceProviderName for legal and illegal qualified class names
 * @modules java.base/jdk.internal.module
 * @run main ChecksWitupTest
 */

import jdk.internal.module.Checks;

public class ChecksWitupTest {

    public static void main(String[] args) {
        testValidNamesAreReturnedUnchanged();
        testNullIsRejected();
        testEmptySegmentIsRejected();
        testLeadingDigitSegmentIsRejected();
        testInvalidCharacterIsRejected();
    }

    static void testValidNamesAreReturnedUnchanged() {
        check("C", Checks.requireServiceProviderName("C"));
        check("p.C", Checks.requireServiceProviderName("p.C"));
        check("a.b.C", Checks.requireServiceProviderName("a.b.C"));
        check("a.b$C", Checks.requireServiceProviderName("a.b$C"));
    }

    static void testNullIsRejected() {
        expectIllegalArgument(null);
    }

    static void testEmptySegmentIsRejected() {
        expectIllegalArgument("p..C");
        expectIllegalArgument(".C");
    }

    static void testLeadingDigitSegmentIsRejected() {
        expectIllegalArgument("1p.C");
    }

    static void testInvalidCharacterIsRejected() {
        expectIllegalArgument("p.C-D");
    }

    static void expectIllegalArgument(String name) {
        try {
            String result = Checks.requireServiceProviderName(name);
            throw new RuntimeException("Expected IllegalArgumentException for " + name
                    + " but got " + result);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but got " + actual);
        }
    }
}
