/*
 * @test
 * @summary Test CheckFieldAdapter.visitAttribute with valid Attribute
 * @run main CheckFieldAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.util.CheckFieldAdapter;

public class CheckFieldAdapterWitupTest {

    public static void main(String[] args) {
        testVisitAttributeWithValidAttribute();
        testVisitAttributeWithNullAttribute();
    }

    static void testVisitAttributeWithValidAttribute() {
        Attribute attr = new Attribute("Test") {};
        CheckFieldAdapter adapter = new CheckFieldAdapter(new DummyFieldVisitor());
        adapter.visitAttribute(attr);
    }

    static void testVisitAttributeWithNullAttribute() {
        CheckFieldAdapter adapter = new CheckFieldAdapter(new DummyFieldVisitor());
        try {
            adapter.visitAttribute(null);
            System.out.println("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }
    }

    static class DummyFieldVisitor extends jdk.internal.org.objectweb.asm.FieldVisitor {
        public DummyFieldVisitor() {
            super(0);
        }
        @Override
        public void visitAttribute(Attribute attr) {
            // no-op
        }
    }
}
