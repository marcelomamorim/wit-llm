/*
 * @test
 * @summary Witup tests for Locale.Builder.removeUnicodeLocaleAttribute
 * @run main LocaleWitupTest
 */

import java.util.IllformedLocaleException;
import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testNullAttributeThrowsNPE();
        testIllformedAttributeThrowsIllformedLocaleException();
        testValidAttributeReturnsSameBuilder();
        System.out.println("All tests passed");
    }

    static void testNullAttributeThrowsNPE() {
        Locale.Builder builder = new Locale.Builder();
        try {
            builder.removeUnicodeLocaleAttribute(null);
            throw new AssertionError("Expected NullPointerException for null attribute");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testIllformedAttributeThrowsIllformedLocaleException() {
        Locale.Builder builder = new Locale.Builder();
        try {
            builder.removeUnicodeLocaleAttribute("a");
            throw new AssertionError("Expected IllformedLocaleException for ill-formed attribute");
        } catch (IllformedLocaleException expected) {
            // expected
        }
    }

    static void testValidAttributeReturnsSameBuilder() {
        Locale.Builder builder = new Locale.Builder();
        Locale.Builder result = builder.removeUnicodeLocaleAttribute("abc");
        if (result != builder) {
            throw new AssertionError("removeUnicodeLocaleAttribute should return the same Builder");
        }
    }
}
