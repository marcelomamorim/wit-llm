/*
 * @test
 * @summary Verifies that ASMifier.visitAnnotation returns a nested visitor and emits the expected generated code
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asm = new ASMifier();
        ASMifier av = asm.visitAnnotation("Lfoo;", "LBar;");

        if (av == null) {
            throw new RuntimeException("visitAnnotation returned null");
        }
        if (av == asm) {
            throw new RuntimeException("visitAnnotation should return a new nested ASMifier");
        }

        StringWriter sw = new StringWriter();
        asm.print(new PrintWriter(sw));
        String out = sw.toString();

        check(out.contains("visitAnnotation("), "missing visitAnnotation call in output");
        check(out.contains("Lfoo;"), "missing name constant in output");
        check(out.contains("LBar;"), "missing desc constant in output");
        check(out.contains("AnnotationVisitor av1 = av0"), "missing nested visitor declaration");
        check(out.contains("}"), "missing closing brace in output");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
