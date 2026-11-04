/*
 * @test
 * @summary Test CheckFieldAdapter.visitAttribute method for exception paths.
 * @run main CheckFieldAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.util.CheckFieldAdapter;

public class CheckFieldAdapterWitupTest {

    public static void main(String[] args) {
        testVisitAttributeWithNull();
        testVisitAttributeWithValidAttribute();
    }

    private static void testVisitAttributeWithNull() {
        CheckFieldAdapter adapter = new CheckFieldAdapter(new DummyFieldVisitor());
        try {
            adapter.visitAttribute(null);
            System.out.println("testVisitAttributeWithNull: Failed - no exception thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testVisitAttributeWithNull: Passed - caught expected IllegalArgumentException");
        } catch (Exception e) {
            System.out.println("testVisitAttributeWithNull: Failed - caught unexpected exception: " + e);
        }
    }

    private static void testVisitAttributeWithValidAttribute() {
        CheckFieldAdapter adapter = new CheckFieldAdapter(new DummyFieldVisitor());
        Attribute attr = new Attribute("Test") {};
        try {
            adapter.visitAttribute(attr);
            System.out.println("testVisitAttributeWithValidAttribute: Passed");
        } catch (Exception e) {
            System.out.println("testVisitAttributeWithValidAttribute: Failed - exception: " + e);
        }
    }

    static class DummyFieldVisitor extends jdk.internal.org.objectweb.asm.FieldVisitor {
        public DummyFieldVisitor() {
            super(0);
        }
    }
}
