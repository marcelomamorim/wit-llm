/*
 * @test
 * @summary Test CheckMethodAdapter.visitAttribute for null and valid Attribute
 * @run main CheckMethodAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.util.CheckMethodAdapter;

public class CheckMethodAdapterWitupTest {

    public static void main(String[] args) {
        testVisitAttributeWithNull();
        testVisitAttributeWithValid();
    }

    static void testVisitAttributeWithNull() {
        CheckMethodAdapter adapter = new CheckMethodAdapter(0, null, null, null, null, null);
        try {
            adapter.visitAttribute(null);
            System.out.println("testVisitAttributeWithNull: Failed - no exception thrown");
        } catch (IllegalArgumentException e) {
            if ("Invalid attribute (must not be null)".equals(e.getMessage())) {
                System.out.println("testVisitAttributeWithNull: Passed");
            } else {
                System.out.println("testVisitAttributeWithNull: Failed - wrong message: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("testVisitAttributeWithNull: Failed - wrong exception: " + e);
        }
    }

    static void testVisitAttributeWithValid() {
        Attribute attr = new Attribute("Test") {};
        CheckMethodAdapter adapter = new CheckMethodAdapter(0, null, null, null, null, null);
        try {
            adapter.visitAttribute(attr);
            System.out.println("testVisitAttributeWithValid: Passed");
        } catch (Exception e) {
            System.out.println("testVisitAttributeWithValid: Failed - exception: " + e);
        }
    }
}
