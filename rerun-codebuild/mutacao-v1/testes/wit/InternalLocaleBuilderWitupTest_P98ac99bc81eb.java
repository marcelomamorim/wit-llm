/* @test    @summary Test for InternalLocaleBuilder.addUnicodeLocaleAttribute() method handling of invalid attribute lengths    @run main InternalLocaleBuilderWitupTest_P98ac99bc81eb */

import sun.util.locale.InternalLocaleBuilder;
import sun.util.locale.LocaleSyntaxException;

public class InternalLocaleBuilderWitupTest_P98ac99bc81eb {

    public static void main(String[] args) throws Exception {
        testAddUnicodeLocaleAttributeInvalid();
    }

    private static void testAddUnicodeLocaleAttributeInvalid() {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        String[] invalidAttributes = {"", "a", "abc", "abcdefgh", "abcdefghij"};
        for (String attr : invalidAttributes) {
            try {
                builder.addUnicodeLocaleAttribute(attr);
                throw new AssertionError("Expected LocaleSyntaxException for attribute: '" + attr + "' but none was thrown.");
            } catch (LocaleSyntaxException e) {
                // Expected exception, test passes for this input
                if (attr.length() > 8 || attr.length() < 3) {
                    // Correctly thrown exception
                } else {
                    throw new AssertionError("Unexpected LocaleSyntaxException for attribute: '" + attr + "' with length: " + attr.length());
                }
            } catch (Exception e) {
                throw new AssertionError("Unexpected exception type: " + e, e);
            }
        }
    }
}
