/*
 * @test
 * @summary Test for Textifier.visitLdcInsn method to observe behavior with various constants.
 * @run main TextifierWitupTest
 */

import java.io.StringWriter;
import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitLdcInsnWithString();
        testVisitLdcInsnWithType();
        testVisitLdcInsnWithInteger();
    }

    private static void testVisitLdcInsnWithString() {
        Textifier t = new Textifier();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.setWriter(pw);
        String testString = "Hello, World!";
        t.visitLdcInsn(testString);
        pw.flush();
        String output = sw.toString();
        if (!output.contains("LDC ") || !output.contains(testString)) {
            throw new AssertionError("Output does not contain expected string: " + output);
        }
    }

    private static void testVisitLdcInsnWithType() {
        Textifier t = new Textifier();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.setWriter(pw);
        Type type = Type.getType(int[].class);
        t.visitLdcInsn(type);
        pw.flush();
        String output = sw.toString();
        if (!output.contains("LDC ") || !output.contains(type.getDescriptor() + ".class")) {
            throw new AssertionError("Output does not contain expected type descriptor: " + output);
        }
    }

    private static void testVisitLdcInsnWithInteger() {
        Textifier t = new Textifier();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.setWriter(pw);
        Integer number = 42;
        t.visitLdcInsn(number);
        pw.flush();
        String output = sw.toString();
        if (!output.contains("LDC ") || !output.contains(number.toString())) {
            throw new AssertionError("Output does not contain expected number: " + output);
        }
    }
}
