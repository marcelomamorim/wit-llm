/*
 * @test
 * @summary Test CheckFieldAdapter.visitEnd() for IllegalStateException on repeated end
 * @run main CheckFieldAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckFieldAdapter;
import jdk.internal.org.objectweb.asm.FieldVisitor;

public class CheckFieldAdapterWitupTest {

    static class DummyFieldVisitor extends FieldVisitor {
        public DummyFieldVisitor() {
            super(0);
        }

        @Override
        public void visitEnd() {
            // no-op
        }
    }

    public static void main(String[] args) {
        FieldVisitor fv = new DummyFieldVisitor();
        CheckFieldAdapter cfa = new CheckFieldAdapter(fv);

        // First call to visitEnd should succeed
        cfa.visitEnd();

        // Second call to visitEnd should throw IllegalStateException
        try {
            cfa.visitEnd();
            throw new Error("Expected IllegalStateException not thrown on second visitEnd call");
        } catch (IllegalStateException ise) {
            // Expected exception
        }
    }
}
