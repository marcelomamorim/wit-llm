/*
 * @test
 * @summary Test for Textifier.visitLineNumber method
 * @run main TextifierWitupTest
 */

import java.io.StringWriter;
import java.io.PrintWriter;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitLineNumberNormal();
        testVisitLineNumberNegativeLine();
        testVisitLineNumberLargeLine();
    }

    static void testVisitLineNumberNormal() {
        jdk.internal.org.objectweb.asm.util.Textifier textifier = new jdk.internal.org.objectweb.asm.util.Textifier();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        // Mock Label object
        jdk.internal.org.objectweb.asm.Label label = new jdk.internal.org.objectweb.asm.Label();
        // Call method
        textifier.visitLineNumber(42, label);
        String output = sw.toString();
        if (!output.contains("LINENUMBER 42")) {
            throw new AssertionError("Expected output to contain 'LINENUMBER 42' but was: " + output);
        }
    }

    static void testVisitLineNumberNegativeLine() {
        jdk.internal.org.objectweb.asm.util.Textifier textifier = new jdk.internal.org.objectweb.asm.util.Textifier();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        jdk.internal.org.objectweb.asm.Label label = new jdk.internal.org.objectweb.asm.Label();
        // Test with negative line number
        textifier.visitLineNumber(-1, label);
        String output = sw.toString();
        if (!output.contains("LINENUMBER -1")) {
            throw new AssertionError("Expected output to contain 'LINENUMBER -1' but was: " + output);
        }
    }

    static void testVisitLineNumberLargeLine() {
        jdk.internal.org.objectweb.asm.util.Textifier textifier = new jdk.internal.org.objectweb.asm.util.Textifier();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        jdk.internal.org.objectweb.asm.Label label = new jdk.internal.org.objectweb.asm.Label();
        int largeLine = Integer.MAX_VALUE;
        textifier.visitLineNumber(largeLine, label);
        String output = sw.toString();
        if (!output.contains("LINENUMBER " + largeLine)) {
            throw new AssertionError("Expected output to contain 'LINENUMBER " + largeLine + "' but was: " + output);
        }
    }
}
