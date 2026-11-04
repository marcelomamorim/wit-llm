/*
 * @test
 * @summary Smoke test: ASMifier.visitMultiANewArrayInsn accepts descriptors and dimension counts without throwing
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        asmifier.visitMultiANewArrayInsn("[[I", 2);
        asmifier.visitMultiANewArrayInsn("Ljava/lang/String;", 1);
        asmifier.visitMultiANewArrayInsn("[[[J", 3);

        ASMifier second = new ASMifier();
        second.visitMultiANewArrayInsn("[[I", 2);
        second.visitMultiANewArrayInsn("[[I", 2);
    }
}
