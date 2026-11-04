/*
 * @test
 * @summary Checks that ASMifier.visitArray returns a new nested visitor and emits the array-start code
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier parent = new ASMifier();
        ASMifier child = parent.visitArray("values");

        if (child == null) {
            throw new AssertionError("visitArray returned null");
        }
        if (child == parent) {
            throw new AssertionError("visitArray must return a new visitor, not this");
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        parent.print(pw);
        pw.flush();
        String out = sw.toString();

        if (!out.contains("AnnotationVisitor av1 = av0.visitArray(\"values\");")) {
            throw new AssertionError("Missing visitArray code in output: " + out);
        }
        if (!out.contains("{\n")) {
            throw new AssertionError("Missing block opening in output: " + out);
        }
    }
}
