/*
 * @test
 * @summary Test ASMifier.visitLineNumber method for correct output
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitLineNumber();
    }

    public static void testVisitLineNumber() {
        ASMifier asmifier = new ASMifier();
        Label label = new Label();
        int lineNumber = 42;
        asmifier.visitLineNumber(lineNumber, label);
        String output = asmifier.text.get(asmifier.text.size() - 1);
        String expectedStart = "ASMifier.visitLineNumber(" + lineNumber + ", ";
        if (!output.startsWith(expectedStart)) {
            throw new AssertionError("Expected output to start with: " + expectedStart + " but was: " + output);
        }
        if (!output.contains(".visitLineNumber(")) {
            throw new AssertionError("Output does not contain method call: " + output);
        }
        if (!output.endsWith(");\n")) {
            throw new AssertionError("Output does not end with expected line ending: " + output);
        }
        System.out.println("testVisitLineNumber passed.");
    }
}
