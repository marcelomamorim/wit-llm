/*
 * @test
 * @summary Test sun.util.locale.InternalLocaleBuilder.addUnicodeLocaleAttribute(String) for valid and invalid inputs
 * @run main InternalLocaleBuilderWitupTest_Pe7ef70ee0281
 */

import sun.util.locale.InternalLocaleBuilder;
import sun.util.locale.LocaleSyntaxException;

public class InternalLocaleBuilderWitupTest_Pe7ef70ee0281 {
    public static void main(String[] args) throws Exception {
        testValidAttribute();
        testInvalidAttributeEmpty();
        testInvalidAttributeTooShort();
        testInvalidAttributeTooLong();
    }

    private static void testValidAttribute() throws Exception {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        // A valid attribute must satisfy UnicodeLocaleExtension.isAttribute
        // Since we cannot call isAttribute directly (not public), test with a known valid attribute
        // According to Unicode Locale Extension spec, attributes are alphanum strings length 3 to 8
        // Use "abc" as a valid attribute
        builder.addUnicodeLocaleAttribute("abc");
        // If no exception, test passed
    }

    private static void testInvalidAttributeEmpty() {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        try {
            builder.addUnicodeLocaleAttribute("");
            throw new RuntimeException("Expected LocaleSyntaxException for empty attribute");
        } catch (LocaleSyntaxException e) {
            // Expected
        }
    }

    private static void testInvalidAttributeTooShort() {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        try {
            builder.addUnicodeLocaleAttribute("ab"); // length 2, too short
            throw new RuntimeException("Expected LocaleSyntaxException for too short attribute");
        } catch (LocaleSyntaxException e) {
            // Expected
        }
    }

    private static void testInvalidAttributeTooLong() {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        try {
            builder.addUnicodeLocaleAttribute("abcdefghi"); // length 9, too long
            throw new RuntimeException("Expected LocaleSyntaxException for too long attribute");
        } catch (LocaleSyntaxException e) {
            // Expected
        }
    }
}
