/*
 * @test
 * @summary Test jdk.internal.org.objectweb.asm.util.Textifier.visitUse(String) method
 * @run main TextifierWitupTest
 */

import java.util.List;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier textifier = new Textifier();

        // Test with a normal internal name descriptor
        textifier.visitUse("Ljava/lang/String;");
        List<String> text = textifier.getText();
        if (text.isEmpty()) {
            throw new Error("Text list should not be empty after visitUse");
        }
        String line = text.get(text.size() - 1);
        if (!line.contains("uses Ljava/lang/String;")) {
            throw new Error("Output line does not contain expected descriptor: " + line);
        }

        // Test with empty string
        textifier.visitUse("");
        String lastLine = textifier.getText().get(textifier.getText().size() - 1);
        if (!lastLine.contains("uses ;")) {
            throw new Error("Output line does not handle empty descriptor correctly: " + lastLine);
        }

        // Test with null - should throw NullPointerException as appendDescriptor likely does not handle null
        boolean npeThrown = false;
        try {
            textifier.visitUse(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new Error("Expected NullPointerException when passing null to visitUse");
        }
    }
}
