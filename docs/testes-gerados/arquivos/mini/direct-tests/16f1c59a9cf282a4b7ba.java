/*
 * @test
 * @summary Test ASMifier.visitUse(String) method for correct output
 * @run main ASMifierWitupTest
 */

import java.util.List;
import java.util.ArrayList;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Test with a normal string
        asmifier.visitUse("TestService");
        List<String> text = asmifier.getText();
        if (text.isEmpty()) {
            throw new AssertionError("Text list should not be empty after visitUse call");
        }
        String lastLine = text.get(text.size() - 1);
        String expected = "mdv.visitUse(\"TestService\");\n";
        if (!lastLine.equals(expected)) {
            throw new AssertionError("Expected: " + expected + ", but got: " + lastLine);
        }

        // Test with empty string
        asmifier.visitUse("");
        lastLine = asmifier.getText().get(asmifier.getText().size() - 1);
        expected = "mdv.visitUse(\"\");\n";
        if (!lastLine.equals(expected)) {
            throw new AssertionError("Expected empty string usage, but got: " + lastLine);
        }

        // Test with null string (should print "null" as string literal)
        asmifier.visitUse(null);
        lastLine = asmifier.getText().get(asmifier.getText().size() - 1);
        expected = "mdv.visitUse(null);\n";
        if (!lastLine.equals(expected)) {
            throw new AssertionError("Expected null usage, but got: " + lastLine);
        }
    }
}
