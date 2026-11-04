/*
 * @test
 * @summary Basic regression test for ASMifier.visitTypeInsn with valid opcodes and types
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier v = new ASMifier();
        v.visitTypeInsn(187, "java/lang/String");
        v.visitTypeInsn(192, "java/lang/Object");
        v.visitTypeInsn(189, "java/lang/Integer");
        v.visitTypeInsn(193, "java/lang/Runnable");

        ASMifier second = new ASMifier();
        second.visitTypeInsn(187, "[I");
        second.visitTypeInsn(187, "java/util/HashMap");
    }
}
