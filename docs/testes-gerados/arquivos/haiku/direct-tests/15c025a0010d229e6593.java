/*
 * @test
 * @summary Verify ASMifier.visitFieldEnd emits the field visitEnd statement observably
 * @modules java.base/jdk.internal.org.objectweb.asm java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;

import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    static class FieldPrinter extends ASMifier {
        FieldPrinter() {
            super(Opcodes.ASM4, "fv", 0);
        }
    }

    static String render(ASMifier p) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        p.print(pw);
        pw.flush();
        return sw.toString();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new AssertionError(msg);
        }
    }

    public static void main(String[] args) {
        FieldPrinter single = new FieldPrinter();
        single.visitFieldEnd();
        String out1 = render(single);
        check(out1.equals("fv.visitEnd();\n"),
              "unexpected output for single visitFieldEnd: [" + out1 + "]");

        FieldPrinter twice = new FieldPrinter();
        twice.visitFieldEnd();
        twice.visitFieldEnd();
        String out2 = render(twice);
        check(out2.equals("fv.visitEnd();\nfv.visitEnd();\n"),
              "unexpected output for repeated visitFieldEnd: [" + out2 + "]");
    }
}
