/*
 * @test
 * @summary Test visitLineNumber method of Textifier for basic functionality and robustness
 * @run main TextifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Textifier;
import jdk.internal.org.objectweb.asm.Label;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitLineNumberBasic();
        testVisitLineNumberWithNullLabel();
    }

    private static void testVisitLineNumberBasic() {
        Textifier textifier = new Textifier();
        Label label = new Label();
        int lineNumber = 42;
        try {
            textifier.visitLineNumber(lineNumber, label);
        } catch (Throwable t) {
            throw new RuntimeException("visitLineNumber threw unexpected exception", t);
        }
        // The text list should contain one entry ending with a newline and containing the line number
        if (textifier.text.isEmpty()) {
            throw new AssertionError("text list is empty after visitLineNumber");
        }
        String output = textifier.text.get(textifier.text.size() - 1);
        if (!output.contains("LINENUMBER " + lineNumber)) {
            throw new AssertionError("Output does not contain expected line number: " + output);
        }
        if (!output.endsWith("\n")) {
            throw new AssertionError("Output does not end with newline: " + output);
        }
    }

    private static void testVisitLineNumberWithNullLabel() {
        Textifier textifier = new Textifier();
        int lineNumber = 0;
        try {
            textifier.visitLineNumber(lineNumber, null);
        } catch (Throwable t) {
            throw new RuntimeException("visitLineNumber threw unexpected exception when label is null", t);
        }
        if (textifier.text.isEmpty()) {
            throw new AssertionError("text list is empty after visitLineNumber with null label");
        }
        String output = textifier.text.get(textifier.text.size() - 1);
        if (!output.contains("LINENUMBER " + lineNumber)) {
            throw new AssertionError("Output does not contain expected line number with null label: " + output);
        }
    }
}
