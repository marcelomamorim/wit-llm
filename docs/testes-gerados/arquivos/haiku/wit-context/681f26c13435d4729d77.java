/*
 * @test
 * @summary Basic observable behavior of ASMifier.visitMultiANewArrayInsn
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {
    public static void main(String[] args) {
        ASMifier asm = new ASMifier();
        asm.visitMultiANewArrayInsn("[[I", 2);
        asm.visitMultiANewArrayInsn("[Ljava/lang/String;", 1);
        asm.visitMultiANewArrayInsn("[I", 0);
        asm.visitMultiANewArrayInsn("[I", -1);
        asm.visitMultiANewArrayInsn("[I", 3);
    }
}
