/*
 * @test
 * @summary Test jdk.internal.org.objectweb.asm.util.Textifier.visitLineNumber method
 * @run main TextifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Textifier;
import jdk.internal.org.objectweb.asm.Label;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier textifier = new Textifier();
        Label label = new Label();

        // Test with a typical line number
        textifier.visitLineNumber(42, label);
        String output = textifier.getText().get(textifier.getText().size() - 1);
        if (!output.contains("LINENUMBER 42 ")) {
            throw new Error("Expected output to contain 'LINENUMBER 42 '");
        }
        if (!output.contains("Label")) {
            // The label's toString() includes 'Label' by default
            throw new Error("Expected output to contain label string");
        }

        // Test with line number zero
        textifier.visitLineNumber(0, label);
        output = textifier.getText().get(textifier.getText().size() - 1);
        if (!output.contains("LINENUMBER 0 ")) {
            throw new Error("Expected output to contain 'LINENUMBER 0 '");
        }

        // Test with negative line number (allowed by method signature, no exception expected)
        textifier.visitLineNumber(-1, label);
        output = textifier.getText().get(textifier.getText().size() - 1);
        if (!output.contains("LINENUMBER -1 ")) {
            throw new Error("Expected output to contain 'LINENUMBER -1 '");
        }

        // Test with null label (should not throw, but label string will be "null")
        try {
            textifier.visitLineNumber(10, null);
            output = textifier.getText().get(textifier.getText().size() - 1);
            if (!output.contains("LINENUMBER 10 null")) {
                throw new Error("Expected output to contain 'LINENUMBER 10 null'");
            }
        } catch (Exception e) {
            throw new Error("visitLineNumber threw exception with null label", e);
        }
    }
}
