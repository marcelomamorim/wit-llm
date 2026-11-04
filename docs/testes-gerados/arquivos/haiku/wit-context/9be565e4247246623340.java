/*
 * @test
 * @summary Verifies that CheckMethodAdapter.visitEnd() forwards the end of the method to the wrapped MethodVisitor
 * @modules java.base/jdk.internal.org.objectweb.asm java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckMethodAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.CheckMethodAdapter;

public class CheckMethodAdapterWitupTest {
    public static void main(String[] args) {
        final int[] ends = {0};
        MethodVisitor target = new MethodVisitor(Opcodes.ASM5) {
            @Override
            public void visitEnd() {
                ends[0]++;
            }
        };
        CheckMethodAdapter cma = new CheckMethodAdapter(target);
        cma.visitEnd();
        if (ends[0] != 1) {
            throw new AssertionError("visitEnd not forwarded exactly once: " + ends[0]);
        }
    }
}
