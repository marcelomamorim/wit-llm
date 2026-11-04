/*
 * @test
 * @summary Smoke test for ASMifier.visitIntInsn with valid opcodes and operands
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier printer = new ASMifier();
        printer.visitIntInsn(Opcodes.BIPUSH, 10);
        printer.visitIntInsn(Opcodes.BIPUSH, -1);
        printer.visitIntInsn(Opcodes.SIPUSH, 1000);
        printer.visitIntInsn(Opcodes.SIPUSH, -1000);
    }
}
