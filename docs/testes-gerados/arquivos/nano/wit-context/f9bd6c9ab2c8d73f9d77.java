/*
 * @test
 * @summary Test visitAttribute method for normal and exceptional behavior
 * @run main CheckMethodAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.util.CheckMethodAdapter;

public class CheckMethodAdapterWitupTest {

    public static void main(String[] args) {
        testVisitAttributeWithValidAttribute();
        testVisitAttributeWithNullAttribute();
    }

    private static void testVisitAttributeWithValidAttribute() {
        try {
            Attribute attr = new Attribute("Test") {};
            CheckMethodAdapter adapter = new CheckMethodAdapter(0, null, null, null, null, null);
            adapter.visitAttribute(attr);
            System.out.println("testVisitAttributeWithValidAttribute passed");
        } catch (Throwable t) {
            System.err.println("testVisitAttributeWithValidAttribute failed: " + t);
        }
    }

    private static void testVisitAttributeWithNullAttribute() {
        try {
            CheckMethodAdapter adapter = new CheckMethodAdapter(0, null, null, null, null, null);
            adapter.visitAttribute(null);
            System.err.println("testVisitAttributeWithNullAttribute failed: no exception thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testVisitAttributeWithNullAttribute passed");
        } catch (Throwable t) {
            System.err.println("testVisitAttributeWithNullAttribute failed: " + t);
        }
    }
}
