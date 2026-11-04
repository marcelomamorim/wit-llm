/*
 * @test
 * @summary Test for visitMethodAttribute method in ASMifier class
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitMethodAttributeWithNull();
        testVisitMethodAttributeWithDummyAttribute();
    }

    public static void testVisitMethodAttributeWithNull() {
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitMethodAttribute(null);
        } catch (NullPointerException e) {
            System.out.println("Passed null attribute test");
            return;
        }
        System.out.println("Failed null attribute test");
    }

    public static void testVisitMethodAttributeWithDummyAttribute() {
        Attribute dummyAttr = new Attribute("Dummy") {};
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitMethodAttribute(dummyAttr);
            System.out.println("Passed dummy attribute test");
        } catch (Exception e) {
            System.out.println("Failed dummy attribute test: " + e);
        }
    }
}
