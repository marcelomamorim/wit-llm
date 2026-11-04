/*
 * @test
 * @summary Witup-generated checks for ASMifier.visitVarInsn: valid opcodes complete normally and an out-of-range opcode fails on the opcode name lookup
 * @modules java.base/jdk.internal.org.objectweb.asm java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier printer = new ASMifier();

        printer.visitVarInsn(Opcodes.ILOAD, 1);
        printer.visitVarInsn(Opcodes.ILOAD, 2);

        expectIndexFailure(printer, -1, 1);
        expectIndexFailure(printer, 100000, 1);
    }

    private static void expectIndexFailure(ASMifier printer, int opcode, int var) {
        try {
            printer.visitVarInsn(opcode, var);
        } catch (ArrayIndexOutOfBoundsException expected) {
            return;
        }
        throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for opcode " + opcode);
    }
}
