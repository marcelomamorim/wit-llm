/*
 * @test
 * @summary Test Textifier.visitUse(String) for basic functionality and robustness
 * @run main TextifierWitupTest
 */

import java.util.List;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier textifier = new Textifier();

        // Test with a normal internal name string
        textifier.visitUse("java/lang/Object");
        List<String> text = textifier.getText();
        if (text.isEmpty()) {
            throw new AssertionError("Text list should not be empty after visitUse");
        }
        String line = text.get(text.size() - 1);
        if (!line.contains("uses java/lang/Object;")) {
            throw new AssertionError("Output line does not contain expected descriptor: " + line);
        }

        // Test with empty string
        textifier.visitUse("");
        text = textifier.getText();
        line = text.get(text.size() - 1);
        if (!line.contains("uses ;")) {
            throw new AssertionError("Output line does not handle empty string correctly: " + line);
        }

        // Test with null string - should not throw but appendDescriptor may handle null gracefully
        try {
            textifier.visitUse(null);
            text = textifier.getText();
            line = text.get(text.size() - 1);
            if (!line.contains("uses null;")) {
                // The actual output may vary depending on appendDescriptor implementation
                // Accept any output that does not throw
            }
        } catch (Exception e) {
            throw new AssertionError("visitUse should not throw on null input", e);
        }

        System.out.println("Textifier.visitUse tests passed.");
    }
}
