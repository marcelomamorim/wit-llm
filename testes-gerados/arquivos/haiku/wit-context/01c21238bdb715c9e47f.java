/*
 * @test
 * @summary Smoke test for Textifier.visitMultiANewArrayInsn with valid descriptors and dimensions
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {
    public static void main(String[] args) {
        Textifier t = new Textifier();
        t.visitMultiANewArrayInsn("[[I", 2);
        t.visitMultiANewArrayInsn("Ljava/lang/String;", 1);
        t.visitMultiANewArrayInsn("[[[J", 3);
        t.visitMultiANewArrayInsn("[[I", 255);
        t.visitMultiANewArrayInsn("[Ljava/lang/Object;", 1);
        if (t == null) {
            throw new AssertionError("Textifier instance must not be null");
        }
    }
}
