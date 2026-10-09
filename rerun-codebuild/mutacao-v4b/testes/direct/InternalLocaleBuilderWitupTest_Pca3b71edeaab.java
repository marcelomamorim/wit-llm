/* @test
   @summary Tests addUnicodeLocaleAttribute method for valid and invalid inputs.
   @run main InternalLocaleBuilderWitupTest_Pca3b71edeaab
 */
import java.util.Objects;
import sun.util.locale.InternalLocaleBuilder;
public class InternalLocaleBuilderWitupTest_Pca3b71edeaab {
    public static void main(String[] args) throws Exception {
        testAddUnicodeLocaleAttributeValid();
        testAddUnicodeLocaleAttributeInvalid();
    }

    private static void testAddUnicodeLocaleAttributeValid() throws Exception {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        builder.addUnicodeLocaleAttribute("abc");
        builder.addUnicodeLocaleAttribute("XYZ");
        builder.addUnicodeLocaleAttribute("123");
    }

    private static void testAddUnicodeLocaleAttributeInvalid() {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        try {
            builder.addUnicodeLocaleAttribute("");
            throw new AssertionError("Expected LocaleSyntaxException for empty attribute");
        } catch (LocaleSyntaxException e) {
            // expected
        }
        try {
            builder.addUnicodeLocaleAttribute("invalid attribute!");
            throw new AssertionError("Expected LocaleSyntaxException for invalid attribute");
        } catch (LocaleSyntaxException e) {
            // expected
        }
    }
}
