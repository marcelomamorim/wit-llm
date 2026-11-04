/*
 * @test
 * @summary Test sun.util.locale.InternalLocaleBuilder.addUnicodeLocaleAttribute method
 * @run main InternalLocaleBuilderWitupTest
 */

import sun.util.locale.InternalLocaleBuilder;
import sun.util.locale.LocaleSyntaxException;

public class InternalLocaleBuilderWitupTest {
    public static void main(String[] args) throws Exception {
        testAddValidUnicodeLocaleAttribute();
        testAddInvalidUnicodeLocaleAttribute();
    }

    private static void testAddValidUnicodeLocaleAttribute() throws Exception {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        // Add a valid attribute (assuming "abc" is valid per UnicodeLocaleExtension.isAttribute)
        // Since we cannot call UnicodeLocaleExtension.isAttribute directly, we rely on the method behavior
        // We pick a valid attribute string that should pass the check
        String validAttr = "abc";
        builder.addUnicodeLocaleAttribute(validAttr);
        // Add the same attribute with different case to test case insensitivity and no exception
        builder.addUnicodeLocaleAttribute("ABC");
        // If no exception thrown, test passed
    }

    private static void testAddInvalidUnicodeLocaleAttribute() {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        String invalidAttr = "invalid-attribute!";
        try {
            builder.addUnicodeLocaleAttribute(invalidAttr);
            throw new RuntimeException("Expected LocaleSyntaxException not thrown for invalid attribute");
        } catch (LocaleSyntaxException e) {
            // Expected exception
            String msg = e.getMessage();
            if (!msg.contains("Ill-formed Unicode locale attribute")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
