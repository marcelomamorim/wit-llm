/* @test    @summary Test removeUnicodeLocaleAttribute method for InternalLocaleBuilder    @run main InternalLocaleBuilderWitupTest */

import sun.util.locale.InternalLocaleBuilder;
import sun.util.locale.LocaleSyntaxException;
import java.util.HashSet;
import java.util.Set;

public class InternalLocaleBuilderWitupTest {

    public static void main(String[] args) throws Exception {
        testRemoveUnicodeLocaleAttributeWithValidAttribute();
        testRemoveUnicodeLocaleAttributeWithNull();
        testRemoveUnicodeLocaleAttributeWithInvalidAttribute();
    }

    static void testRemoveUnicodeLocaleAttributeWithValidAttribute() throws Exception {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        // Simulate adding an attribute
        builder.uattributes = new HashSet<>();
        builder.uattributes.add(new sun.util.locale.CaseInsensitiveString("testAttr"));
        // Remove the attribute
        builder.removeUnicodeLocaleAttribute("testAttr");
        assert !builder.uattributes.contains(new sun.util.locale.CaseInsensitiveString("testAttr")) : "Attribute should be removed";
    }

    static void testRemoveUnicodeLocaleAttributeWithNull() {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        try {
            builder.removeUnicodeLocaleAttribute(null);
            assert false : "Expected LocaleSyntaxException for null attribute";
        } catch (LocaleSyntaxException e) {
            assert e.getMessage().contains("Ill-formed Unicode locale attribute") : "Exception message mismatch";
        }
    }

    static void testRemoveUnicodeLocaleAttributeWithInvalidAttribute() {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        try {
            builder.removeUnicodeLocaleAttribute("Invalid@Attr");
            assert false : "Expected LocaleSyntaxException for invalid attribute";
        } catch (LocaleSyntaxException e) {
            assert e.getMessage().contains("Ill-formed Unicode locale attribute") : "Exception message mismatch";
        }
    }
}
