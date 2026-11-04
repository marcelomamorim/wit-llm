/*
 * @test
 * @summary Test Textifier.visitMaxs method for correct output formatting
 * @run main TextifierWitupTest
 */

import java.util.List;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier textifier = new Textifier();

        // Call visitMaxs with sample values
        textifier.visitMaxs(5, 10);

        // Retrieve the text list and verify the last two entries
        List<String> text = textifier.getText();

        // The last two entries should correspond to MAXSTACK and MAXLOCALS lines
        String maxStackLine = text.get(text.size() - 2);
        String maxLocalsLine = text.get(text.size() - 1);

        // Check that the lines contain the expected substrings
        if (!maxStackLine.contains("MAXSTACK = 5")) {
            throw new AssertionError("Expected MAXSTACK line to contain 'MAXSTACK = 5', but was: " + maxStackLine);
        }

        if (!maxLocalsLine.contains("MAXLOCALS = 10")) {
            throw new AssertionError("Expected MAXLOCALS line to contain 'MAXLOCALS = 10', but was: " + maxLocalsLine);
        }

        // Also test zero values
        textifier.visitMaxs(0, 0);
        text = textifier.getText();
        maxStackLine = text.get(text.size() - 2);
        maxLocalsLine = text.get(text.size() - 1);

        if (!maxStackLine.contains("MAXSTACK = 0")) {
            throw new AssertionError("Expected MAXSTACK line to contain 'MAXSTACK = 0', but was: " + maxStackLine);
        }

        if (!maxLocalsLine.contains("MAXLOCALS = 0")) {
            throw new AssertionError("Expected MAXLOCALS line to contain 'MAXLOCALS = 0', but was: " + maxLocalsLine);
        }
    }
}
