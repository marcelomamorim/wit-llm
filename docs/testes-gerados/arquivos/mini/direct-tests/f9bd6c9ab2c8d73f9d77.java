/*
 * @test
 * @summary Test visitAttribute method of CheckMethodAdapter for null and non-null attributes
 * @run main CheckMethodAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckMethodAdapter;
import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.MethodVisitor;

public class CheckMethodAdapterWitupTest {

    static class DummyMethodVisitor extends MethodVisitor {
        boolean visited = false;
        Attribute lastAttr = null;

        public DummyMethodVisitor() {
            super(0);
        }

        @Override
        public void visitAttribute(final Attribute attr) {
            visited = true;
            lastAttr = attr;
        }
    }

    public static void main(String[] args) {
        DummyMethodVisitor dmv = new DummyMethodVisitor();
        CheckMethodAdapter cma = new CheckMethodAdapter(dmv);

        // Test 1: Passing null attribute should throw IllegalArgumentException
        try {
            cma.visitAttribute(null);
            throw new Error("Expected IllegalArgumentException for null attribute");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("must not be null")) {
                throw new Error("IllegalArgumentException message unexpected: " + msg);
            }
        }

        // Test 2: Passing a non-null attribute should call super.visitAttribute
        Attribute attr = new Attribute("Test") {};
        cma.visitAttribute(attr);
        if (!dmv.visited) {
            throw new Error("super.visitAttribute was not called");
        }
        if (dmv.lastAttr != attr) {
            throw new Error("Attribute passed to super.visitAttribute is not the same instance");
        }
    }
}
