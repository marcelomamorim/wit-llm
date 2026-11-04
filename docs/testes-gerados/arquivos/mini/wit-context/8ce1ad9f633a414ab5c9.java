/*
 * @test
 * @summary Test Textifier.visitAttribute method behavior
 * @run main TextifierWitupTest
 */

import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier textifier = new Textifier();

        // Test with a simple Attribute subclass that is NOT Textifiable
        Attribute unknownAttr = new Attribute("UnknownType") {};
        textifier.visitAttribute(unknownAttr);
        String lastText = textifier.text.get(textifier.text.size() - 1);
        if (!lastText.contains("ATTRIBUTE UnknownType") || !lastText.contains(" : unknown")) {
            throw new AssertionError("visitAttribute failed for unknown Attribute type");
        }

        // Test with a Textifiable Attribute implementation
        Attribute textifiableAttr = new Attribute("TextifiableType") {
            @Override
            public String type() { return "TextifiableType"; }
        };

        // We need a Textifiable interface implementation as anonymous class
        // Since Textifiable is not in imports, define minimal interface here
        // and cast to it to test the branch
        interface Textifiable {
            void textify(StringBuffer buffer, Object unused);
        }

        Attribute textifiableAttrWithTextify = new Attribute("TextifiableType") implements Textifiable {
            public void textify(StringBuffer buffer, Object unused) {
                buffer.append("textified content\n");
            }
        };

        // Because Java 11 does not allow anonymous class to implement interface in this way,
        // we create a named subclass instead
        class TextifiableAttribute extends Attribute implements Textifiable {
            TextifiableAttribute() { super("TextifiableType"); }
            public void textify(StringBuffer buffer, Object unused) {
                buffer.append("textified content\n");
            }
        }

        TextifiableAttribute ta = new TextifiableAttribute();
        textifier.visitAttribute(ta);
        lastText = textifier.text.get(textifier.text.size() - 1);
        if (!lastText.contains("ATTRIBUTE TextifiableType") || !lastText.contains("textified content")) {
            throw new AssertionError("visitAttribute failed for Textifiable Attribute");
        }
    }
}
