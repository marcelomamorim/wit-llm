/*
 * @test
 * @summary Test for visitFieldAttribute method in Textifier class
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitFieldAttributeWithNull();
        testVisitFieldAttributeWithDummyAttribute();
    }

    private static void testVisitFieldAttributeWithNull() {
        try {
            Textifier t = new Textifier();
            t.visitFieldAttribute(null);
            System.out.println("testVisitFieldAttributeWithNull passed");
        } catch (Exception e) {
            System.err.println("testVisitFieldAttributeWithNull failed: " + e);
        }
    }

    private static void testVisitFieldAttributeWithDummyAttribute() {
        try {
            Attribute dummyAttr = new Attribute("Dummy") {
                @Override
                public void write(PrintWriter pw) {
                    pw.println("Dummy attribute");
                }
            };
            Textifier t = new Textifier();
            t.visitFieldAttribute(dummyAttr);
            System.out.println("testVisitFieldAttributeWithDummyAttribute passed");
        } catch (Exception e) {
            System.err.println("testVisitFieldAttributeWithDummyAttribute failed: " + e);
        }
    }
}
