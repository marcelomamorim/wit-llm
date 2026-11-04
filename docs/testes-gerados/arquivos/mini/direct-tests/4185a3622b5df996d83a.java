/*
 * @test
 * @summary Test jdk.internal.org.objectweb.asm.util.Textifier.visitSource method
 * @run main TextifierWitupTest
 */

import java.util.List;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier textifier = new Textifier();

        // Test with both file and debug null: no output added
        textifier.visitSource(null, null);
        if (!textifier.text.isEmpty()) {
            throw new AssertionError("Expected empty text list when both file and debug are null");
        }

        // Test with only file non-null
        textifier.visitSource("TestFile.java", null);
        List<String> text1 = textifier.text;
        if (text1.isEmpty()) {
            throw new AssertionError("Expected non-empty text list when file is non-null");
        }
        String line1 = text1.get(text1.size() - 1);
        if (!line1.contains("// compiled from: TestFile.java")) {
            throw new AssertionError("Expected line to contain file info");
        }

        // Clear text for next test
        textifier.text.clear();

        // Test with only debug non-null
        textifier.visitSource(null, "DebugInfo");
        List<String> text2 = textifier.text;
        if (text2.isEmpty()) {
            throw new AssertionError("Expected non-empty text list when debug is non-null");
        }
        String line2 = text2.get(text2.size() - 1);
        if (!line2.contains("// debug info: DebugInfo")) {
            throw new AssertionError("Expected line to contain debug info");
        }

        // Clear text for next test
        textifier.text.clear();

        // Test with both file and debug non-null
        textifier.visitSource("File.java", "DebugData");
        List<String> text3 = textifier.text;
        if (text3.size() != 2) {
            throw new AssertionError("Expected two lines when both file and debug are non-null");
        }
        String lineFile = text3.get(0);
        String lineDebug = text3.get(1);
        if (!lineFile.contains("// compiled from: File.java")) {
            throw new AssertionError("First line should contain file info");
        }
        if (!lineDebug.contains("// debug info: DebugData")) {
            throw new AssertionError("Second line should contain debug info");
        }
    }
}
