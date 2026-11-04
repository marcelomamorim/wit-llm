/*
 * @test
 * @summary Verifies CheckMethodAdapter.visitAttribute rejects null attributes and delegates non-null attributes
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckMethodAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.CheckMethodAdapter;

public class CheckMethodAdapterWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static class RecordingVisitor extends MethodVisitor {
        Attribute received;
        int calls;

        RecordingVisitor() {
            super(Opcodes.ASM5);
        }

        @Override
        public void visitAttribute(Attribute attr) {
            received = attr;
            calls++;
        }
    }

    static void testNullAttributeThrows() {
        RecordingVisitor rv = new RecordingVisitor();
        CheckMethodAdapter cma = new CheckMethodAdapter(rv);
        boolean thrown = false;
        try {
            cma.visitAttribute(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
            check("Invalid attribute (must not be null)".equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
        }
        check(thrown, "null attribute must throw IllegalArgumentException");
        check(rv.calls == 0, "null attribute must not be delegated");
    }

    static void testNonNullAttributeDelegated() {
        RecordingVisitor rv = new RecordingVisitor();
        CheckMethodAdapter cma = new CheckMethodAdapter(rv);
        Attribute attr = new Attribute("WitupAttr") {};
        cma.visitAttribute(attr);
        check(rv.calls == 1, "non-null attribute must be delegated once");
        check(rv.received == attr, "delegated attribute must be the same instance");
    }

    static void testMultipleAttributesDelegated() {
        RecordingVisitor rv = new RecordingVisitor();
        CheckMethodAdapter cma = new CheckMethodAdapter(rv);
        Attribute a1 = new Attribute("A1") {};
        Attribute a2 = new Attribute("A2") {};
        cma.visitAttribute(a1);
        cma.visitAttribute(a2);
        check(rv.calls == 2, "each non-null attribute must be delegated");
        check(rv.received == a2, "last delegated attribute must be a2");
    }

    public static void main(String[] args) {
        testNullAttributeThrows();
        testNonNullAttributeDelegated();
        testMultipleAttributesDelegated();
    }
}
