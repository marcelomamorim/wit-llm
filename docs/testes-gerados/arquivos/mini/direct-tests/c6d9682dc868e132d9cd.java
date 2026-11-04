/*
 * @test
 * @summary Test ASMifier.visitLineNumber method behavior
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();
        Label label = new Label();

        // Call visitLineNumber with a typical line number and label
        asmifier.visitLineNumber(42, label);

        // The text list should contain one entry with the expected format
        if (asmifier.text.isEmpty()) {
            throw new AssertionError("text list is empty after visitLineNumber call");
        }

        String output = asmifier.text.get(asmifier.text.size() - 1);

        // Check that output contains the line number and label string representation
        if (!output.contains(".visitLineNumber(42, ")) {
            throw new AssertionError("Output does not contain expected line number call: " + output);
        }

        if (!output.endsWith(");\n")) {
            throw new AssertionError("Output does not end with ');\n': " + output);
        }

        // Call visitLineNumber with line number zero and a new label
        Label label2 = new Label();
        asmifier.visitLineNumber(0, label2);

        String output2 = asmifier.text.get(asmifier.text.size() - 1);
        if (!output2.contains(".visitLineNumber(0, ")) {
            throw new AssertionError("Output does not contain expected line number 0 call: " + output2);
        }

        // Call visitLineNumber with negative line number (allowed by signature, no exception expected)
        Label label3 = new Label();
        asmifier.visitLineNumber(-1, label3);

        String output3 = asmifier.text.get(asmifier.text.size() - 1);
        if (!output3.contains(".visitLineNumber(-1, ")) {
            throw new AssertionError("Output does not contain expected negative line number call: " + output3);
        }
    }
}
