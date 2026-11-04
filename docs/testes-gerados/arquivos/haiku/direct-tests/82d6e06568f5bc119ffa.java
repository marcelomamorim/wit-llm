/*
 * @test
 * @summary Checks ASMifier.visitVarInsn accepts valid opcodes and rejects out-of-range opcodes
 * @modules java.base/jdk.internal.org.objectweb.asm.util java.base/jdk.internal.org.objectweb.asm
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) throws Exception {
        ASMifier printer = new ASMifier();

        printer.visitVarInsn(Opcodes.ILOAD, 1);
        printer.visitVarInsn(Opcodes.ALOAD, 0);
        printer.visitVarInsn(Opcodes.ISTORE, 300);

        boolean negativeThrown = false;
        try {
            printer.visitVarInsn(-1, 0);
        } catch (ArrayIndexOutOfBoundsException e) {
            negativeThrown = true;
        }
        if (!negativeThrown) {
            throw new RuntimeException("expected ArrayIndexOutOfBoundsException for opcode -1");
        }

        boolean largeThrown = false;
        try {
            printer.visitVarInsn(100000, 0);
        } catch (ArrayIndexOutOfBoundsException e) {
            largeThrown = true;
        }
        if (!largeThrown) {
            throw new RuntimeException("expected ArrayIndexOutOfBoundsException for opcode 100000");
        }

        printer.visitVarInsn(Opcodes.LLOAD, 2);
    }
}
