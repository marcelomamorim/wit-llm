/*
 * @test
 * @summary Test jdk.internal.org.objectweb.asm.util.Textifier.visitLabel(Label) method
 * @run main TextifierWitupTest
 */

import java.util.List;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier textifier = new Textifier();
        Label label = new Label();

        // Call visitLabel with a new Label
        textifier.visitLabel(label);

        // The text list should contain one string ending with a newline
        List<String> text = textifier.getText();
        if (text == null || text.size() != 1) {
            throw new AssertionError("Expected text list size 1, got: " + (text == null ? "null" : text.size()));
        }

        String line = text.get(0);
        if (!line.endsWith("\n")) {
            throw new AssertionError("Expected line to end with newline character");
        }

        // The line should contain the label representation (appendLabel appends label info)
        // We cannot assert exact content because appendLabel is inherited and internal,
        // but the line should start with ltab string (which is "  " by default in Textifier)
        if (!line.startsWith("  ")) {
            throw new AssertionError("Expected line to start with ltab indentation");
        }

        // Call visitLabel again with the same label to check multiple calls
        textifier.visitLabel(label);
        if (textifier.getText().size() != 2) {
            throw new AssertionError("Expected text list size 2 after second visitLabel call");
        }

        System.out.println("Test passed.");
    }
}
