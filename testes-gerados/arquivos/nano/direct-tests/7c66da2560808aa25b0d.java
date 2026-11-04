/*
 * @test
 * @summary Test for visitIincInsn method in Textifier class
 * @run main TextifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitIincInsn();
    }

    public static void testVisitIincInsn() {
        Textifier textifier = new Textifier();
        int varIndex = 3;
        int incrementValue = 5;
        textifier.visitIincInsn(varIndex, incrementValue);
        String lastLine = textifier.text.get(textifier.text.size() - 1);
        String expected = "\t\tIINC 3 5\n";
        if (!lastLine.equals(expected)) {
            throw new AssertionError("Expected: " + expected + ", but got: " + lastLine);
        }
    }
}
