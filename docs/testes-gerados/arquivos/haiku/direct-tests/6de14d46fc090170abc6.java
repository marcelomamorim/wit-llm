/*
 * @test
 * @summary Verifies ASMifier.visitIntInsn emits the expected ASM builder code for BIPUSH, SIPUSH and NEWARRAY
 * @modules java.base/jdk.internal.org.objectweb.asm java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import java.util.List;

import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    static class Probe extends ASMifier {
        Probe() {
            super(Opcodes.ASM5, "mv", 0);
        }

        String last() {
            List<?> t = text;
            return String.valueOf(t.get(t.size() - 1));
        }

        int size() {
            return text.size();
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        Probe p = new Probe();
        check(p.size() == 0, "initial text must be empty");

        p.visitIntInsn(Opcodes.BIPUSH, 42);
        check(p.size() == 1, "one entry expected after first call");
        check("mv.visitIntInsn(BIPUSH, 42);\n".equals(p.last()),
              "unexpected BIPUSH output: " + p.last());

        p.visitIntInsn(Opcodes.SIPUSH, -5);
        check(p.size() == 2, "two entries expected after second call");
        check("mv.visitIntInsn(SIPUSH, -5);\n".equals(p.last()),
              "unexpected SIPUSH output: " + p.last());

        Probe q = new Probe();
        q.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_INT);
        String arr = q.last();
        check(arr.startsWith("mv.visitIntInsn(NEWARRAY, "),
              "unexpected NEWARRAY prefix: " + arr);
        check(arr.contains("T_INT"), "NEWARRAY operand must be rendered as a type name: " + arr);
        check(arr.endsWith(");\n"), "NEWARRAY output must end with ');' and newline: " + arr);
    }
}
