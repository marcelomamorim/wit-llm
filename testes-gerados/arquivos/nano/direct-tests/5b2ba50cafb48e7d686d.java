/*
 * @test
 * @summary Test the visitArray method of Textifier for correct behavior.
 * @run main TextifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitArrayWithName();
        testVisitArrayWithoutName();
    }

    private static void testVisitArrayWithName() {
        Textifier t = new Textifier();
        String name = "TestArray";
        Textifier result = t.visitArray(name);
        String textOutput = result.getText();
        if (!textOutput.contains("=") || !textOutput.contains("{") || !textOutput.contains("}") || !textOutput.contains(name + '=')) {
            throw new AssertionError("visitArray with name failed: " + textOutput);
        }
    }

    private static void testVisitArrayWithoutName() {
        Textifier t = new Textifier();
        String name = null;
        Textifier result = t.visitArray(name);
        String textOutput = result.getText();
        if (textOutput.contains("=") || !textOutput.contains("{") || !textOutput.contains("}") || textOutput.contains("=")) {
            throw new AssertionError("visitArray without name failed: " + textOutput);
        }
    }
}
