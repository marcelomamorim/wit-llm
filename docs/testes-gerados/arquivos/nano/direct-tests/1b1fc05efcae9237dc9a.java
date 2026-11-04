/*
 * @test
 * @summary Test visitFieldAttribute method of Textifier class
 * @run main TextifierWitupTest
 */

import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitFieldAttributeWithNull();
        testVisitFieldAttributeWithSampleAttribute();
    }

    static void testVisitFieldAttributeWithNull() {
        Textifier textifier = new Textifier();
        try {
            textifier.visitFieldAttribute(null);
        } catch (Exception e) {
            System.out.println("Failed with null Attribute: " + e);
        }
    }

    static void testVisitFieldAttributeWithSampleAttribute() {
        Attribute attr = new Attribute("SampleAttribute") {};
        Textifier textifier = new Textifier();
        try {
            textifier.visitFieldAttribute(attr);
            System.out.println("visitFieldAttribute with sample Attribute executed successfully.");
        } catch (Exception e) {
            System.out.println("Failed with sample Attribute: " + e);
        }
    }
}
