/*
 * @test
 * @summary Test visitParameter method of TraceMethodVisitor
 * @run main TraceMethodVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.util.TraceMethodVisitor;
import jdk.internal.org.objectweb.asm.util.Printer;
import jdk.internal.org.objectweb.asm.util.TextPrinter;

public class TraceMethodVisitorWitupTest {

    public static void main(String[] args) {
        testVisitParameter();
    }

    public static void testVisitParameter() {
        Printer printer = new TextPrinter();
        TraceMethodVisitor visitor = new TraceMethodVisitor(printer);
        String paramName = "param1";
        int accessFlags = 0x0001;
        visitor.visitParameter(paramName, accessFlags);
        String output = printer.getText();
        if (!output.contains("visitParameter")) {
            throw new AssertionError("Output does not contain visitParameter call") ;
        }
        if (!output.contains(paramName)) {
            throw new AssertionError("Output does not contain parameter name") ;
        }
        if (!output.contains(Integer.toString(accessFlags))) {
            throw new AssertionError("Output does not contain access flags") ;
        }
    }
}
