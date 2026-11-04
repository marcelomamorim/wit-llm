/*
 * @test
 * @summary Test ASMifier.visitArray method for basic functionality and output format
 * @run main ASMifierWitupTest
 */

import java.util.List;
import java.util.ArrayList;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();
        ASMifier result = asmifier.visitArray("testName");

        if (result == null) {
            throw new AssertionError("visitArray returned null");
        }

        // The text list should contain the expected formatted strings
        List<String> text = getText(result);

        if (text == null || text.isEmpty()) {
            throw new AssertionError("visitArray did not produce any text output");
        }

        // Check that the first string starts with '{\n'
        String first = text.get(0);
        if (!first.startsWith("{\n")) {
            throw new AssertionError("First text element does not start with '{\\n': " + first);
        }

        // Check that the last string ends with '}\n'
        String last = text.get(text.size() - 1);
        if (!last.endsWith("}\n")) {
            throw new AssertionError("Last text element does not end with '}\\n': " + last);
        }

        // Check that the text contains the visitor variable name and id usage
        boolean containsVisitor = false;
        for (String s : text) {
            if (s.contains("AnnotationVisitor av") && s.contains(".visitArray(")) {
                containsVisitor = true;
                break;
            }
        }
        if (!containsVisitor) {
            throw new AssertionError("Text output does not contain expected visitor variable usage");
        }

        System.out.println("ASMifier.visitArray test passed.");
    }

    // Reflection-like access to the protected 'text' field of ASMifier
    // Since 'text' is protected in ASMifier, and ASMifier extends Printer,
    // and no public getter is provided, we rely on subclassing to expose it.
    private static List<String> getText(ASMifier asmifier) {
        if (asmifier instanceof ASMifierWithTextAccess) {
            return ((ASMifierWithTextAccess) asmifier).getText();
        } else {
            // Wrap the ASMifier instance in a subclass that exposes text
            ASMifierWithTextAccess wrapper = new ASMifierWithTextAccess(asmifier);
            return wrapper.getText();
        }
    }

    // Subclass to expose the protected 'text' field
    private static class ASMifierWithTextAccess extends ASMifier {
        private final ASMifier delegate;

        ASMifierWithTextAccess(ASMifier delegate) {
            super();
            this.delegate = delegate;
        }

        @Override
        public List<String> getText() {
            try {
                java.lang.reflect.Field textField = ASMifier.class.getDeclaredField("text");
                textField.setAccessible(true);
                @SuppressWarnings("unchecked")
                List<String> text = (List<String>) textField.get(delegate);
                return text;
            } catch (Exception e) {
                throw new RuntimeException("Failed to access text field", e);
            }
        }
    }
}