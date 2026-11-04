/*
 * @test
 * @summary Test for visitParameter method in TraceMethodVisitor
 * @run main TraceMethodVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.util.TraceMethodVisitor;
import jdk.internal.org.objectweb.asm.util.Printer;
import jdk.internal.org.objectweb.asm.util.TextPrinter;

public class TraceMethodVisitorWitupTest {

    public static void main(String[] args) {
        testVisitParameterNormal();
        testVisitParameterWithNullName();
        testVisitParameterWithEmptyName();
    }

    static void testVisitParameterNormal() {
        Printer printer = new TextPrinter();
        TraceMethodVisitor visitor = new TraceMethodVisitor(printer);
        try {
            visitor.visitParameter("paramName", 0);
            System.out.println("testVisitParameterNormal passed");
        } catch (Exception e) {
            System.err.println("testVisitParameterNormal failed: " + e);
        }
    }

    static void testVisitParameterWithNullName() {
        Printer printer = new TextPrinter();
        TraceMethodVisitor visitor = new TraceMethodVisitor(printer);
        try {
            visitor.visitParameter(null, 1);
            System.out.println("testVisitParameterWithNullName passed");
        } catch (Exception e) {
            System.err.println("testVisitParameterWithNullName failed: " + e);
        }
    }

    static void testVisitParameterWithEmptyName() {
        Printer printer = new TextPrinter();
        TraceMethodVisitor visitor = new TraceMethodVisitor(printer);
        try {
            visitor.visitParameter("", 2);
            System.out.println("testVisitParameterWithEmptyName passed");
        } catch (Exception e) {
            System.err.println("testVisitParameterWithEmptyName failed: " + e);
        }
    }
}
