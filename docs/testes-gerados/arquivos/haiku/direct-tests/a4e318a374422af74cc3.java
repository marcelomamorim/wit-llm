/*
 * @test
 * @summary ASMifier.visitInsn emits the ASM code for a no-operand instruction and rejects invalid opcodes
 * @modules java.base/jdk.internal.org.objectweb.asm java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import java.util.List;

import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static String last(ASMifier printer) {
        List<?> text = printer.getText();
        check(text.size() > 0, "no text produced");
        return String.valueOf(text.get(text.size() - 1));
    }

    public static void main(String[] args) {
        ASMifier printer = new ASMifier();

        printer.visitInsn(Opcodes.NOP);
        String nop = last(printer);
        check(nop.endsWith(".visitInsn(NOP);\n"), "unexpected NOP output: " + nop);

        printer.visitInsn(Opcodes.RETURN);
        String ret = last(printer);
        check(ret.endsWith(".visitInsn(RETURN);\n"), "unexpected RETURN output: " + ret);
        check(!ret.contains("NOP"), "buffer was not reset between calls: " + ret);

        ASMifier other = new ASMifier();
        other.visitInsn(Opcodes.ACONST_NULL);
        String aconst = last(other);
        check(aconst.endsWith(".visitInsn(ACONST_NULL);\n"), "unexpected ACONST_NULL output: " + aconst);

        boolean thrown = false;
        try {
            printer.visitInsn(-1);
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "invalid opcode -1 should fail on OPCODES lookup");
    }
}
