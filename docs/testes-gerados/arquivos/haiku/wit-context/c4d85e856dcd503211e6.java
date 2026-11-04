/*
 * @test
 * @summary Witup-derived tests for InternalLocaleBuilder.addUnicodeLocaleAttribute
 * @modules java.base/sun.util.locale
 * @run main InternalLocaleBuilderWitupTest
 */
import sun.util.locale.InternalLocaleBuilder;
import sun.util.locale.LocaleSyntaxException;

public class InternalLocaleBuilderWitupTest {
    public static void main(String[] args) throws Exception {
        InternalLocaleBuilder b = new InternalLocaleBuilder();
        if (b.addUnicodeLocaleAttribute("abc") != b) {
            throw new AssertionError("addUnicodeLocaleAttribute must return this builder");
        }
        new InternalLocaleBuilder().addUnicodeLocaleAttribute("abcdefgh");
        new InternalLocaleBuilder().addUnicodeLocaleAttribute("ABC");
        expectSyntaxError("");
        expectSyntaxError("ab");
        expectSyntaxError("abcdefghi");
    }

    private static void expectSyntaxError(String attr) {
        try {
            new InternalLocaleBuilder().addUnicodeLocaleAttribute(attr);
        } catch (LocaleSyntaxException e) {
            return;
        }
        throw new AssertionError("expected LocaleSyntaxException for: " + attr);
    }
}
