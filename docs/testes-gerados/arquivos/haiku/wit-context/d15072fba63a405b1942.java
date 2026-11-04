/*
 * @test
 * @summary Smoke and repeated-call tests for ASMifier.visitJumpInsn
 * @modules java.base/jdk.internal.org.objectweb.asm
 *          java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();
        Label label = new Label();

        asmifier.visitJumpInsn(Opcodes.GOTO, label);

        Label second = new Label();
        asmifier.visitJumpInsn(Opcodes.GOTO, second);
        asmifier.visitJumpInsn(Opcodes.GOTO, label);

        ASMifier other = new ASMifier();
        other.visitJumpInsn(Opcodes.GOTO, new Label());
    }
}
