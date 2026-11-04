/*
 * @test
 * @summary Test visitLineNumber method of Textifier class
 * @run main TextifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Textifier;
import jdk.internal.org.objectweb.asm.Label;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitLineNumber();
    }

    public static void testVisitLineNumber() {
        Textifier textifier = new Textifier();
        Label label = new Label();
        int lineNumber = 42;
        textifier.visitLineNumber(lineNumber, label);
        String output = textifier.text.get(textifier.text.size() - 1);
        String expectedStart = "\tLINENUMBER 42 ";
        if (!output.startsWith(expectedStart)) {
            throw new AssertionError("Expected output to start with: " + expectedStart + " but was: " + output);
        }
        if (!output.contains("\n")) {
            throw new AssertionError("Output should contain a newline character.");
        }
        System.out.println("visitLineNumber test passed.");
    }
}
