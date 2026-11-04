/*
 * @test
 * @summary Basic observable behavior of ASMifier.visitAnnotation
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */
import java.io.PrintWriter;
import java.io.StringWriter;

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitAnnotationReturnsVisitor();
        testVisitAnnotationEmitsCode();
        testRepeatedCallsDoNotFail();
    }

    static void testVisitAnnotationReturnsVisitor() {
        ASMifier printer = new ASMifier();
        ASMifier av = printer.visitAnnotation("Ljava/lang/Deprecated;", "x");
        if (av == null) {
            throw new AssertionError("visitAnnotation returned null");
        }
    }

    static void testVisitAnnotationEmitsCode() {
        ASMifier printer = new ASMifier();
        printer.visitAnnotation("Ljava/lang/Deprecated;", "x");
        StringWriter sw = new StringWriter();
        printer.print(new PrintWriter(sw));
        String out = sw.toString();
        check(out.contains("AnnotationVisitor av1 = av0.visitAnnotation("), out);
        check(out.contains("\"Ljava/lang/Deprecated;\""), out);
        check(out.contains("\"x\""), out);
    }

    static void testRepeatedCallsDoNotFail() {
        ASMifier printer = new ASMifier();
        ASMifier first = printer.visitAnnotation("LA;", "y");
        ASMifier second = printer.visitAnnotation("LB;", "z");
        if (first == null || second == null) {
            throw new AssertionError("visitAnnotation returned null on repeated call");
        }
    }

    static void check(boolean condition, String out) {
        if (!condition) {
            throw new AssertionError("Unexpected output: " + out);
        }
    }
}
