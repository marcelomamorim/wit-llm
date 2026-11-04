/*
 * @test
 * @summary Test visitAnnotationDefault method of CheckMethodAdapter
 * @run main CheckMethodAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.AnnotationVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.util.CheckMethodAdapter;

public class CheckMethodAdapterWitupTest {

    static class DummyMethodVisitor extends MethodVisitor {
        boolean called = false;
        DummyMethodVisitor() {
            super(0);
        }
        @Override
        public AnnotationVisitor visitAnnotationDefault() {
            called = true;
            return null;
        }
    }

    public static void main(String[] args) {
        DummyMethodVisitor dmv = new DummyMethodVisitor();
        CheckMethodAdapter cma = new CheckMethodAdapter(dmv);

        AnnotationVisitor av = cma.visitAnnotationDefault();

        if (!dmv.called) {
            throw new Error("super.visitAnnotationDefault() was not called");
        }
        if (av == null) {
            throw new Error("visitAnnotationDefault should not return null");
        }
    }
}
