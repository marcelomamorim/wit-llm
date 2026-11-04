/*
 * @test
 * @summary Test for ASMifier.visitMethodAttribute method.
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitMethodAttributeWithNull();
        testVisitMethodAttributeWithSampleAttribute();
    }

    public static void testVisitMethodAttributeWithNull() {
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitMethodAttribute(null);
        } catch (NullPointerException e) {
            System.out.println("Passed null attribute test");
            return;
        }
        throw new AssertionError("Expected NullPointerException for null attribute");
    }

    public static void testVisitMethodAttributeWithSampleAttribute() {
        Attribute attr = new Attribute("Sample") {};
        ASMifier asmifier = new ASMifier();
        asmifier.visitMethodAttribute(attr);
        System.out.println("Visited sample attribute successfully");
    }
}
