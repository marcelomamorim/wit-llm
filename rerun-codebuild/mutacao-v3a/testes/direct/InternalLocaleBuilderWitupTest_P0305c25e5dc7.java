/* @test    @summary Test InternalLocaleBuilder.addUnicodeLocaleAttribute for valid and invalid inputs    @run main InternalLocaleBuilderWitupTest_P0305c25e5dc7 */

import java.util.HashSet;
import java.util.Set;
import sun.util.locale.InternalLocaleBuilder;
import sun.util.locale.LocaleSyntaxException;

public class InternalLocaleBuilderWitupTest_P0305c25e5dc7 {

    public static void main(String[] args) {
        testAddUnicodeLocaleAttributeValid();
        testAddUnicodeLocaleAttributeInvalid();
    }

    static void testAddUnicodeLocaleAttributeValid() {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        try {
            builder.addUnicodeLocaleAttribute("foo");
            builder.addUnicodeLocaleAttribute("bar");
            // No exception should be thrown, verify internal state
            // Since internal state is private, test behavior indirectly
            // e.g., adding same attribute again should not cause error
            builder.addUnicodeLocaleAttribute("foo");
        } catch (LocaleSyntaxException e) {
            throw new AssertionError("Unexpected exception for valid attribute", e);
        }
    }

    static void testAddUnicodeLocaleAttributeInvalid() {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        try {
            builder.addUnicodeLocaleAttribute("Invalid-Attribute!");
            throw new AssertionError("Expected LocaleSyntaxException was not thrown");
        } catch (LocaleSyntaxException e) {
            // Expected exception, test passes
            String message = e.getMessage();
            if (!message.contains("Ill-formed Unicode locale attribute")) {
                throw new AssertionError("Unexpected exception message: " + message);
            }
        }
    }
}
