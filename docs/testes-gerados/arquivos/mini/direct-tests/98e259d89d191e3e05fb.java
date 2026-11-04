/*
 * @test
 * @summary Test jdk.internal.org.objectweb.asm.util.Textifier.visitPackage method
 * @run main TextifierWitupTest
 */

import java.util.List;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier textifier = new Textifier();

        // Test with a normal package name
        textifier.visitPackage("java/lang");
        List<String> text = textifier.getText();
        if (text.isEmpty()) {
            throw new AssertionError("Text list should not be empty after visitPackage");
        }
        String line = text.get(text.size() - 1);
        if (!line.equals("  // package java/lang\n")) {
            throw new AssertionError("Unexpected output: " + line);
        }

        // Test with empty string package name
        textifier.visitPackage("");
        text = textifier.getText();
        line = text.get(text.size() - 1);
        if (!line.equals("  // package \n")) {
            throw new AssertionError("Unexpected output for empty package: " + line);
        }

        // Test with null package name (should not throw, but output will be "null")
        textifier.visitPackage(null);
        text = textifier.getText();
        line = text.get(text.size() - 1);
        if (!line.equals("  // package null\n")) {
            throw new AssertionError("Unexpected output for null package: " + line);
        }
    }
}
