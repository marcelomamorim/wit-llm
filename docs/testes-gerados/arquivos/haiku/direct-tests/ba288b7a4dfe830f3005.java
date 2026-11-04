/*
 * @test
 * @summary CheckClassAdapter.visitEnd delegates to the next visitor after a valid visit
 * @modules java.base/jdk.internal.org.objectweb.asm java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckClassAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.CheckClassAdapter;

public class CheckClassAdapterWitupTest {

    public static void main(String[] args) {
        checkDelegatesVisitEnd(new CheckClassAdapter(newCountingVisitor(new int[1]), false), true);
        checkDelegatesVisitEnd(new CheckClassAdapter(newCountingVisitor(new int[1])), false);
    }

    static ClassVisitor newCountingVisitor(final int[] count) {
        return new ClassVisitor(Opcodes.ASM5) {
            @Override
            public void visitEnd() {
                count[0]++;
            }
        };
    }

    static void checkDelegatesVisitEnd(CheckClassAdapter cca, boolean unused) {
        final int[] count = new int[1];
        ClassVisitor next = newCountingVisitor(count);
        CheckClassAdapter adapter = new CheckClassAdapter(next, false);
        adapter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "Foo", null, "java/lang/Object", null);
        adapter.visitEnd();
        if (count[0] != 1) {
            throw new RuntimeException("visitEnd not delegated exactly once: " + count[0]);
        }
    }
}
