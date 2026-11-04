/*
 * @test
 * @summary Verify CheckMethodAdapter.visitAnnotationDefault returns an annotation visitor and rejects calls after the method has ended
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckMethodAdapterWitupTest
 */
import jdk.internal.org.objectweb.asm.AnnotationVisitor;
import jdk.internal.org.objectweb.asm.util.CheckMethodAdapter;

public class CheckMethodAdapterWitupTest {
    public static void main(String[] args) {
        CheckMethodAdapter cma = new CheckMethodAdapter(null);
        AnnotationVisitor av = cma.visitAnnotationDefault();
        if (av == null) {
            throw new RuntimeException("expected non-null AnnotationVisitor");
        }
        cma.visitEnd();
        try {
            cma.visitAnnotationDefault();
            throw new RuntimeException("expected IllegalStateException after visitEnd");
        } catch (IllegalStateException expected) {
        }
    }
}
