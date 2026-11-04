/*
 * @test
 * @summary Test visitAttribute method of CheckMethodAdapter for exception and normal behavior
 * @run main CheckMethodAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckMethodAdapter;
import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Label;
import java.util.HashMap;

public class CheckMethodAdapterWitupTest {

    static class DummyMethodVisitor extends MethodVisitor {
        DummyMethodVisitor() {
            super(0);
        }

        boolean visited = false;
        Attribute lastAttr = null;

        @Override
        public void visitAttribute(final Attribute attr) {
            visited = true;
            lastAttr = attr;
        }
    }

    public static void main(String[] args) {
        testVisitAttributeNullThrows();
        testVisitAttributeAfterEndMethodThrows();
        testVisitAttributeNormal();
    }

    private static void testVisitAttributeNullThrows() {
        DummyMethodVisitor dmv = new DummyMethodVisitor();
        CheckMethodAdapter cma = new CheckMethodAdapter(dmv);
        // endMethod is false by default, so passing null should throw IllegalArgumentException
        try {
            cma.visitAttribute(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown for null attribute");
        } catch (IllegalArgumentException iae) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }

    private static void testVisitAttributeAfterEndMethodThrows() {
        DummyMethodVisitor dmv = new DummyMethodVisitor();
        CheckMethodAdapter cma = new CheckMethodAdapter(dmv);
        // Use reflection to set endMethod to true to simulate end of method
        try {
            java.lang.reflect.Field endMethodField = CheckMethodAdapter.class.getDeclaredField("endMethod");
            endMethodField.setAccessible(true);
            endMethodField.setBoolean(cma, true);
        } catch (Exception e) {
            throw new RuntimeException("Reflection failed to set endMethod", e);
        }
        try {
            cma.visitAttribute(new Attribute("Test"));
            throw new RuntimeException("Expected IllegalStateException not thrown when endMethod is true");
        } catch (IllegalStateException ise) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }

    private static void testVisitAttributeNormal() {
        DummyMethodVisitor dmv = new DummyMethodVisitor();
        CheckMethodAdapter cma = new CheckMethodAdapter(dmv);
        Attribute attr = new Attribute("Test") {
            @Override
            protected Attribute read(final java.io.DataInput in, final int length, final char[] buf, final int codeOff, final Label[] labels) {
                return null;
            }

            @Override
            protected ByteVector write(final ByteVector out) {
                return out;
            }
        };
        cma.visitAttribute(attr);
        if (!dmv.visited) {
            throw new RuntimeException("visitAttribute was not delegated to MethodVisitor");
        }
        if (dmv.lastAttr != attr) {
            throw new RuntimeException("Attribute passed to MethodVisitor is not the same instance");
        }
    }
}
