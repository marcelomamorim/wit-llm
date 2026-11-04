/*
 * @test
 * @summary Test jdk.internal.org.objectweb.asm.util.ASMifier.visitSource method
 * @run main ASMifierWitupTest
 */

import java.util.List;
import java.util.ArrayList;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Test with normal non-null strings
        asmifier.visitSource("TestFile.java", "DebugInfo");
        List<String> text = asmifier.getText();
        if (text.isEmpty()) {
            throw new AssertionError("Expected non-empty text after visitSource call");
        }
        String output = text.get(text.size() - 1);
        if (!output.contains("cw.visitSource(")) {
            throw new AssertionError("Output does not contain expected visitSource call");
        }

        // Test with null parameters
        asmifier.visitSource(null, null);
        text = asmifier.getText();
        if (text.isEmpty()) {
            throw new AssertionError("Expected non-empty text after visitSource call with nulls");
        }
        output = text.get(text.size() - 1);
        if (!output.contains("cw.visitSource(")) {
            throw new AssertionError("Output does not contain expected visitSource call with nulls");
        }

        // Test with empty strings
        asmifier.visitSource("", "");
        text = asmifier.getText();
        if (text.isEmpty()) {
            throw new AssertionError("Expected non-empty text after visitSource call with empty strings");
        }
        output = text.get(text.size() - 1);
        if (!output.contains("cw.visitSource(")) {
            throw new AssertionError("Output does not contain expected visitSource call with empty strings");
        }

        System.out.println("ASMifier.visitSource tests passed.");
    }
}
