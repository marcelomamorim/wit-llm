/*
 * @test
 * @summary Verifies Textifier.visitArray returns a nested Textifier and emits array delimiters
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;

import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testReturnsNewNestedTextifier();
        testUnnamedArrayPrintsBraces();
        testNamedArrayPrintsNameAndBrace();
    }

    static String print(Textifier t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.print(pw);
        pw.flush();
        return sw.toString();
    }

    static void testReturnsNewNestedTextifier() {
        Textifier root = new Textifier();
        Textifier nested = root.visitArray(null);
        if (nested == null) {
            throw new RuntimeException("visitArray returned null");
        }
        if (nested == root) {
            throw new RuntimeException("visitArray must return a new Textifier");
        }
    }

    static void testUnnamedArrayPrintsBraces() {
        Textifier root = new Textifier();
        root.visitArray(null);
        String out = print(root);
        if (!out.contains("{") || !out.contains("}")) {
            throw new RuntimeException("Missing array braces in output: " + out);
        }
        if (out.contains("=")) {
            throw new RuntimeException("Unnamed array must not print a name assignment: " + out);
        }
    }

    static void testNamedArrayPrintsNameAndBrace() {
        Textifier root = new Textifier();
        root.visitArray("arr");
        String out = print(root);
        if (!out.contains("arr={")) {
            throw new RuntimeException("Expected 'arr={' in output: " + out);
        }
        if (!out.contains("}")) {
            throw new RuntimeException("Missing closing brace in output: " + out);
        }
    }
}
