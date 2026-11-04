/*
 * @test
 * @summary Checks CheckAnnotationAdapter.visitEnd delegation and null-delegate behavior
 * @modules java.base/jdk.internal.org.objectweb.asm java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckAnnotationAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.AnnotationVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.CheckAnnotationAdapter;

public class CheckAnnotationAdapterWitupTest {

    public static void main(String[] args) {
        CheckAnnotationAdapter nullDelegate =
                new CheckAnnotationAdapter((AnnotationVisitor) null);
        nullDelegate.visitEnd();

        final int[] counter = {0};
        AnnotationVisitor delegate = new AnnotationVisitor(Opcodes.ASM6) {
            @Override
            public void visitEnd() {
                counter[0]++;
            }
        };
        CheckAnnotationAdapter adapter = new CheckAnnotationAdapter(delegate);
        adapter.visitEnd();
        if (counter[0] != 1) {
            throw new RuntimeException("visitEnd not delegated exactly once: " + counter[0]);
        }
    }
}
