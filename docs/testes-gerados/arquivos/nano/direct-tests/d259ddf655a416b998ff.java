/*
 * @test
 * @summary Test for Textifier.visitMaxs method to verify output correctness.
 * @run main TextifierWitupTest
 */

import java.util.List;
import java.util.ArrayList;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitMaxsOutputs();
    }

    public static void testVisitMaxsOutputs() {
        Textifier textifier = new Textifier();
        List<String> outputLines = new ArrayList<>();

        // Override the text list to capture output
        textifier.text = outputLines;

        // Call visitMaxs with sample values
        int sampleMaxStack = 10;
        int sampleMaxLocals = 20;
        textifier.visitMaxs(sampleMaxStack, sampleMaxLocals);

        // Verify that the output contains the expected lines
        boolean maxStackLineFound = false;
        boolean maxLocalsLineFound = false;

        for (String line : outputLines) {
            if (line.contains("MAXSTACK = ") && line.contains(Integer.toString(sampleMaxStack))) {
                maxStackLineFound = true;
            }
            if (line.contains("MAXLOCALS = ") && line.contains(Integer.toString(sampleMaxLocals))) {
                maxLocalsLineFound = true;
            }
        }

        if (!maxStackLineFound) {
            throw new AssertionError("Output does not contain expected MAXSTACK line.");
        }
        if (!maxLocalsLineFound) {
            throw new AssertionError("Output does not contain expected MAXLOCALS line.");
        }
    }
}
