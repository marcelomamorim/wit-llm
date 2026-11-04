/*
 * @test
 * @summary Test jdk.internal.org.objectweb.asm.util.Textifier.visitAttribute behavior
 * @run main TextifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Textifier;
import jdk.internal.org.objectweb.asm.Attribute;

public class TextifierWitupTest {

    static class DummyAttribute extends Attribute {
        DummyAttribute(String type) {
            super(type);
        }
    }

    static class TextifiableAttribute extends Attribute implements Textifier.Textifiable {
        TextifiableAttribute(String type) {
            super(type);
        }

        @Override
        public void textify(StringBuilder buffer, Object o) {
            buffer.append("textified content\n");
        }
    }

    public static void main(String[] args) {
        Textifier textifier = new Textifier();

        // Test with Attribute not implementing Textifiable
        Attribute dummy = new DummyAttribute("DummyType");
        textifier.visitAttribute(dummy);
        String lastLine = textifier.text.get(textifier.text.size() - 1);
        if (!lastLine.startsWith("    ATTRIBUTE DummyType")) {
            throw new AssertionError("Expected line to start with ATTRIBUTE DummyType but was: " + lastLine);
        }
        if (!lastLine.endsWith(" : unknown\n")) {
            throw new AssertionError("Expected line to end with ' : unknown\n' but was: " + lastLine);
        }

        // Test with Attribute implementing Textifiable
        TextifiableAttribute textifiable = new TextifiableAttribute("TextifiableType");
        textifier.visitAttribute(textifiable);
        lastLine = textifier.text.get(textifier.text.size() - 1);
        if (!lastLine.startsWith("    ATTRIBUTE TextifiableType")) {
            throw new AssertionError("Expected line to start with ATTRIBUTE TextifiableType but was: " + lastLine);
        }
        if (!lastLine.contains("textified content")) {
            throw new AssertionError("Expected line to contain 'textified content' but was: " + lastLine);
        }
    }
}
