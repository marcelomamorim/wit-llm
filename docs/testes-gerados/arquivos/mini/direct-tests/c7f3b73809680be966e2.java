/*
 * @test
 * @summary Test jdk.internal.org.objectweb.asm.util.Textifier.visitMainClass behavior
 * @run main TextifierWitupTest
 */

import java.util.List;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier textifier = new Textifier();

        // Test with a normal class name
        textifier.visitMainClass("com/example/Main");
        List<String> text = textifier.getText();
        String lastLine = text.get(text.size() - 1);
        if (!lastLine.equals("  // main class com/example/Main\n")) {
            throw new AssertionError("Unexpected output for normal class name: " + lastLine);
        }

        // Test with empty string
        textifier.visitMainClass("");
        text = textifier.getText();
        lastLine = text.get(text.size() - 1);
        if (!lastLine.equals("  // main class \n")) {
            throw new AssertionError("Unexpected output for empty string: " + lastLine);
        }

        // Test with null (should not throw, but append "null" string)
        textifier.visitMainClass(null);
        text = textifier.getText();
        lastLine = text.get(text.size() - 1);
        if (!lastLine.equals("  // main class null\n")) {
            throw new AssertionError("Unexpected output for null: " + lastLine);
        }
    }
}
