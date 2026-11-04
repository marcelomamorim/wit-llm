/*
 * @test
 * @summary Test for Textifier.visitAttribute method to verify handling of Attribute objects.
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitAttributeWithNull();
        testVisitAttributeWithUnknown();
    }

    private static void testVisitAttributeWithNull() {
        Textifier textifier = new Textifier();
        try {
            textifier.visitAttribute(null);
            System.out.println("testVisitAttributeWithNull passed");
        } catch (Exception e) {
            System.err.println("testVisitAttributeWithNull failed: " + e);
        }
    }

    private static void testVisitAttributeWithUnknown() {
        Textifier textifier = new Textifier();
        Attribute unknownAttr = new Attribute("UnknownType") {};
        try {
            textifier.visitAttribute(unknownAttr);
            System.out.println("testVisitAttributeWithUnknown passed");
        } catch (Exception e) {
            System.err.println("testVisitAttributeWithUnknown failed: " + e);
        }
    }
}
