/* @test    @summary Test adding Unicode locale attribute with invalid length    @run main InternalLocaleBuilderWitupTest_P0305c25e5dc7 */

import sun.util.locale.InternalLocaleBuilder;
import sun.util.locale.LocaleSyntaxException;

public class InternalLocaleBuilderWitupTest_P0305c25e5dc7 {

    public static void main(String[] args) {
        testAddUnicodeLocaleAttributeInvalid();
        testAddUnicodeLocaleAttributeValid();
    }

    private static void testAddUnicodeLocaleAttributeInvalid() {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        String[] invalidAttributes = {"", "a", "abc", "abcdefgh", "ab"};
        for (String attr : invalidAttributes) {
            try {
                builder.addUnicodeLocaleAttribute(attr);
                System.out.println("FAIL: Expected LocaleSyntaxException for attribute='" + attr + "'");
            } catch (LocaleSyntaxException e) {
                System.out.println("PASS: Caught expected exception for attribute='" + attr + "'");
            }
        }
    }

    private static void testAddUnicodeLocaleAttributeValid() {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        String[] validAttributes = {"abc", "attribute1", "ATTRIBUTE2"};
        for (String attr : validAttributes) {
            try {
                builder.addUnicodeLocaleAttribute(attr);
                System.out.println("PASS: Successfully added attribute='" + attr + "'");
            } catch (LocaleSyntaxException e) {
                System.out.println("FAIL: Unexpected exception for attribute='" + attr + "'");
            }
        }
    }
}
