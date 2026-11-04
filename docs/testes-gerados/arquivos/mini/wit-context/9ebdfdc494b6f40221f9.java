/*
 * @test
 * @summary Test deterministic behavior of ASMifier.visitAnnotationEnd()
 * @run main ASMifierWitupTest
 */

import java.util.ArrayList;
import java.util.List;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Before visitAnnotationEnd, text list should be empty
        if (!asmifier.text.isEmpty()) {
            throw new AssertionError("text list should be empty initially");
        }

        // Call visitAnnotationEnd and check that text list has exactly one element
        asmifier.visitAnnotationEnd();
        List<String> textAfter = asmifier.text;
        if (textAfter.size() != 1) {
            throw new AssertionError("text list should contain exactly one element after visitAnnotationEnd");
        }

        // The element should be "av" + id + ".visitEnd();\n"
        String expected = "av" + asmifier.id + ".visitEnd();\n";
        String actual = textAfter.get(0);
        if (!expected.equals(actual)) {
            throw new AssertionError("Unexpected text content: expected '" + expected + "' but got '" + actual + "'");
        }

        // The buffer should be reset to length 0 after call
        // We cannot access buf directly (protected), but we can call visitAnnotationEnd again and check text size increments
        asmifier.visitAnnotationEnd();
        if (asmifier.text.size() != 2) {
            throw new AssertionError("text list should contain two elements after second visitAnnotationEnd call");
        }

        // Check that the second element is also as expected
        String secondExpected = "av" + asmifier.id + ".visitEnd();\n";
        String secondActual = asmifier.text.get(1);
        if (!secondExpected.equals(secondActual)) {
            throw new AssertionError("Unexpected second text content: expected '" + secondExpected + "' but got '" + secondActual + "'");
        }

        System.out.println("ASMifier.visitAnnotationEnd() test passed.");
    }
}
