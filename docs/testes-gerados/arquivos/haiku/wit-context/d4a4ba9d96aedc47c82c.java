/*
 * @test
 * @summary Witup-derived smoke tests for Textifier.visitTypeInsn
 * @modules java.base/jdk.internal.org.objectweb.asm
 *          java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {
    public static void main(String[] args) {
        Textifier t = new Textifier();
        t.visitTypeInsn(Opcodes.NEW, "java/lang/String");
        t.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object");
        t.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Runnable");
        t.visitTypeInsn(Opcodes.INSTANCEOF, "[I");

        Textifier t2 = new Textifier();
        for (int i = 0; i < 1000; i++) {
            t2.visitTypeInsn(Opcodes.NEW, "a/B" + i);
        }
        t2.visitTypeInsn(Opcodes.CHECKCAST, "a/Final");
    }
}
