/*
 * @test
 * @summary Test CheckFieldAdapter.visitAttribute method behavior
 * @run main CheckFieldAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckFieldAdapter;
import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.Attribute;

public class CheckFieldAdapterWitupTest {

    public static void main(String[] args) {
        testVisitAttributeWithNull();
        testVisitAttributeWithValidAttribute();
    }

    private static void testVisitAttributeWithNull() {
        CheckFieldAdapter cfa = new CheckFieldAdapter(new FieldVisitor(262144 /* Opcodes.ASM7 */) {});
        try {
            cfa.visitAttribute(null);
            throw new Error("Expected IllegalArgumentException for null attribute");
        } catch (IllegalArgumentException e) {
            if (!"Invalid attribute (must not be null)".equals(e.getMessage())) {
                throw new Error("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testVisitAttributeWithValidAttribute() {
        final boolean[] called = new boolean[1];
        FieldVisitor fv = new FieldVisitor(262144 /* Opcodes.ASM7 */) {
            @Override
            public void visitAttribute(Attribute attr) {
                called[0] = true;
                if (attr == null) {
                    throw new Error("Attribute should not be null");
                }
            }
        };
        CheckFieldAdapter cfa = new CheckFieldAdapter(fv);
        Attribute attr = new Attribute("Test") {
            @Override
            protected Attribute read(ClassReader cr, int off, int len, char[] buf, int codeOff, Label[] labels) {
                return null;
            }

            @Override
            protected ByteVector write(ClassWriter cw, byte[] code, int len, int maxStack, int maxLocals) {
                return null;
            }
        };
        cfa.visitAttribute(attr);
        if (!called[0]) {
            throw new Error("super.visitAttribute was not called");
        }
    }
}
