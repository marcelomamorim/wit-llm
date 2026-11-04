/*
 * @test
 * @summary Smoke and out-of-range opcode behavior of ASMifier.visitInsn
 * @modules java.base/jdk.internal.org.objectweb.asm
 *          java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier valid = new ASMifier();
        valid.visitInsn(Opcodes.NOP);
        valid.visitInsn(Opcodes.RETURN);

        expectArrayIndexOutOfBounds(-1);
        expectArrayIndexOutOfBounds(1000);

        System.out.println("ASMifierWitupTest passed");
    }

    static void expectArrayIndexOutOfBounds(int opcode) {
        ASMifier v = new ASMifier();
        try {
            v.visitInsn(opcode);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for opcode " + opcode);
    }
}
