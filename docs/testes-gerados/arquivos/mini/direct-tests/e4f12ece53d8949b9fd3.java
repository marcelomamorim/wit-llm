/*
 * @test
 * @summary Test ASMifier.visitMaxs method for correct output formatting
 * @run main ASMifierWitupTest
 */

import java.util.List;
import java.util.ArrayList;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Call visitMaxs with sample values
        asmifier.visitMaxs(5, 10);

        // Retrieve the text list and verify the last entry
        List<String> text = asmifier.getText();
        if (text == null || text.isEmpty()) {
            throw new AssertionError("Text list is null or empty after visitMaxs call");
        }

        String lastLine = text.get(text.size() - 1);
        String expected = "asm.visitMaxs(5, 10);\n";

        if (!lastLine.equals(expected)) {
            throw new AssertionError("Expected: '" + expected + "' but got: '" + lastLine + "'");
        }

        // Test with zero values
        asmifier.visitMaxs(0, 0);
        lastLine = asmifier.getText().get(asmifier.getText().size() - 1);
        expected = "asm.visitMaxs(0, 0);\n";
        if (!lastLine.equals(expected)) {
            throw new AssertionError("Expected: '" + expected + "' but got: '" + lastLine + "'");
        }

        // Test with negative values (allowed by method signature, no exception expected)
        asmifier.visitMaxs(-1, -2);
        lastLine = asmifier.getText().get(asmifier.getText().size() - 1);
        expected = "asm.visitMaxs(-1, -2);\n";
        if (!lastLine.equals(expected)) {
            throw new AssertionError("Expected: '" + expected + "' but got: '" + lastLine + "'");
        }
    }
}
