/*
 * @test
 * @summary Test visitAttribute method of CheckFieldAdapter for exception paths
 * @run main CheckFieldAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckFieldAdapter;
import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.Attribute;

public class CheckFieldAdapterWitupTest {

    static class DummyFieldVisitor extends FieldVisitor {
        public DummyFieldVisitor() {
            super(0);
        }

        @Override
        public void visitAttribute(final Attribute attr) {
            // no-op
        }
    }

    public static void main(String[] args) {
        // Create a CheckFieldAdapter instance wrapping a dummy FieldVisitor
        CheckFieldAdapter cfa = new CheckFieldAdapter(new DummyFieldVisitor());

        // Test 1: call visitAttribute with null attribute when end is false
        // Expect IllegalArgumentException
        try {
            cfa.visitAttribute(null);
            throw new Error("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException iae) {
            // expected
        } catch (Exception e) {
            throw new Error("Unexpected exception type: " + e);
        }

        // Test 2: simulate end == true to trigger IllegalStateException on visitAttribute
        // Since 'end' is private, we cannot set it directly; instead, we call visitEnd() to set it
        cfa.visitEnd();

        try {
            cfa.visitAttribute(new Attribute("Dummy"));
            throw new Error("Expected IllegalStateException not thrown");
        } catch (IllegalStateException ise) {
            // expected
        } catch (Exception e) {
            throw new Error("Unexpected exception type: " + e);
        }

        // Test 3: call visitAttribute with a valid Attribute when end is false
        // Create a fresh CheckFieldAdapter to avoid end == true
        CheckFieldAdapter cfa2 = new CheckFieldAdapter(new DummyFieldVisitor());
        Attribute attr = new Attribute("Test") {
            @Override
            public byte[] getContent() {
                return new byte[0];
            }
        };
        try {
            cfa2.visitAttribute(attr);
        } catch (Exception e) {
            throw new Error("Unexpected exception thrown for valid attribute: " + e);
        }
    }
}
